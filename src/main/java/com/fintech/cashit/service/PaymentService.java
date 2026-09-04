package com.fintech.cashit.service;

import com.fintech.cashit.DTO.PaymentRequestDTO;
import com.fintech.cashit.DTO.PaymentResponseDTO;
import com.fintech.cashit.entity.*;
import com.fintech.cashit.exception.PaymentNotFoundException;
import com.fintech.cashit.exception.PaymentStatusException;
import com.fintech.cashit.repository.OrderRepository;
import com.fintech.cashit.repository.PaymentRepository;
import com.fintech.cashit.repository.TransactionRepository;
import com.razorpay.RazorpayClient;
import jakarta.transaction.Transactional;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class PaymentService {
    @Autowired
    private RazorpayClient razorpayClient;
    @Autowired
    private TransactionRepository transactionRepository;

    @Autowired
    private PaymentRepository paymentRepository;

    @Autowired
    private OrderRepository orderRepository;

    public Payment createPayment(
            PaymentRequestDTO request,
            Authentication authentication) {

        User user = (User) authentication.getPrincipal();

        Order order = orderRepository
                .findByIdAndUser(request.getOrderId(), user)
                .orElseThrow(() -> new RuntimeException("Order not found"));

        if (order.getStatus() == OrderStatus.PAID) {
            throw new PaymentStatusException("Order is already paid");
        }

        if (request.getAmount().compareTo(order.getAmount()) != 0) {
            throw new PaymentStatusException(
                    "Payment amount does not match order amount"
            );
        }

        Optional<Payment> existingPayment =
                paymentRepository.findByOrderAndStatus(order, PaymentStatus.PENDING);

        if (existingPayment.isPresent()) {
            throw new PaymentStatusException(
                    "Order already has a pending payment"
            );
        }




        Payment payment = new Payment();
        payment.setAmount(request.getAmount());
        payment.setCurrency(order.getCurrency());
        payment.setOrder(order);
        payment.setStatus(PaymentStatus.PENDING);
        payment.setPaymentReference(UUID.randomUUID().toString());
        payment.setCreatedAt(LocalDateTime.now());

        try {
            JSONObject razorpayOrderRequest = new JSONObject();


            long amountInPaise = request.getAmount()
                    .multiply(new java.math.BigDecimal("100"))
                    .longValueExact();

            razorpayOrderRequest.put("amount", amountInPaise);
            razorpayOrderRequest.put("currency", order.getCurrency());
            razorpayOrderRequest.put("receipt", payment.getPaymentReference());

            com.razorpay.Order razorpayOrder =
                    razorpayClient.orders.create(razorpayOrderRequest);

            payment.setRazorpayOrderId(razorpayOrder.get("id").toString());

        } catch (Exception e) {
            throw new PaymentStatusException(
                    "Failed to create Razorpay order"
            );
        }

        return paymentRepository.save(payment);


    }

    @Transactional
    public Payment confirmPayment(
            Long paymentId,
            Authentication authentication) {


        User user = (User) authentication.getPrincipal();

        Payment payment = paymentRepository
                .findByIdAndOrder_User(paymentId, user)
                .orElseThrow(() -> new PaymentNotFoundException("Payment not found"));

        if (payment.getStatus() != PaymentStatus.PENDING) {
            throw new PaymentStatusException("Payment cannot be confirmed");
        }

        payment.setStatus(PaymentStatus.SUCCESS);

        Order order = payment.getOrder();
        order.setStatus(OrderStatus.PAID);
        orderRepository.save(order);

        Transaction transaction = new Transaction();
        transaction.setAmount(payment.getAmount());
        transaction.setType(TransactionType.PAYMENT);
        transaction.setStatus(TransactionStatus.SUCCESS);
        transaction.setUser(user);
        transaction.setCreatedAt(LocalDateTime.now());
        transaction.setTransactionReference(UUID.randomUUID().toString());
        transaction.setDescription("Payment for order " + order.getOrderReference());
        transaction.setCurrency(payment.getCurrency());
        transaction.setPayment(payment);


        transactionRepository.save(transaction);

        return paymentRepository.save(payment);
    }

    public List<Payment> getUserPayments(Authentication authentication) {
        User user = (User) authentication.getPrincipal();
        return paymentRepository.findByOrder_User(user);
    }

    public PaymentResponseDTO convertToDTO(Payment payment) {
        PaymentResponseDTO dto = new PaymentResponseDTO();

        dto.setId(payment.getId());
        dto.setAmount(payment.getAmount());
        dto.setStatus(payment.getStatus());
        dto.setOrderId(payment.getOrder().getId());
        dto.setPaymentReference(payment.getPaymentReference());
        dto.setCreatedAt(payment.getCreatedAt());
        dto.setCurrency(payment.getCurrency());

        return dto;
    }
}