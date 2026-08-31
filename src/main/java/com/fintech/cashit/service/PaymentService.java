package com.fintech.cashit.service;

import com.fintech.cashit.DTO.PaymentResponseDTO;
import com.fintech.cashit.entity.*;
import com.fintech.cashit.exception.PaymentNotFoundException;
import com.fintech.cashit.exception.PaymentStatusException;
import com.fintech.cashit.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fintech.cashit.repository.OrderRepository;
import com.fintech.cashit.repository.PaymentRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import com.fintech.cashit.DTO.PaymentRequestDTO;

@Service
public class PaymentService {
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
                .orElseThrow(()-> new RuntimeException("Order nOT Found"));
        if (order.getStatus() == OrderStatus.PAID) {
            throw new RuntimeException("Order is already paid");
        }

        Payment payment = new Payment();

        payment.setAmount(request.getAmount());
        payment.setOrder(order);
        payment.setStatus(PaymentStatus.PENDING);
        payment.setPaymentReference(UUID.randomUUID().toString());
        payment.setCreatedAt(LocalDateTime.now());

        if (request.getAmount().compareTo(order.getAmount()) != 0) {
            throw new RuntimeException("Payment amount does not match order amount");
        }
        Optional<Payment> existingPayment =
                paymentRepository.findByOrderAndStatus(
                        order,
                        PaymentStatus.PENDING
                );

        if (existingPayment.isPresent()) {
            throw new PaymentStatusException(
                    "Order already has a pending payment"
            );
        }


        return paymentRepository.save(payment);
    }
    public PaymentResponseDTO convertToDTO(Payment payment) {

        PaymentResponseDTO dto = new PaymentResponseDTO();

        dto.setId(payment.getId());
        dto.setAmount(payment.getAmount());
        dto.setStatus(payment.getStatus());
        dto.setOrderId(payment.getOrder().getId());
        dto.setPaymentReference(payment.getPaymentReference());
        dto.setCreatedAt(payment.getCreatedAt());

        return dto;
    }
    public Payment confirmPayment(
            Long paymentId,
            Authentication authentication) {

        User user = (User) authentication.getPrincipal();

        Payment payment = paymentRepository
                .findByIdAndUser_User(paymentId, user)
                .orElseThrow(() -> new PaymentNotFoundException("Payment not found"));

        if (payment.getStatus() != PaymentStatus.PENDING) {
            throw new PaymentStatusException("Payment cannot be confirmed");
        }


        payment.setStatus(PaymentStatus.SUCCESS);
        Order order=payment.getOrder();
        order.setStatus(OrderStatus.PAID);
        orderRepository.save(order);

        Transaction transaction = new Transaction();

        transaction.setAmount(payment.getAmount());
        transaction.setType(TransactionType.PAYMENT);
        transaction.setStatus(TransactionStatus.SUCCESS);
        transaction.setUser(user);
        transaction.setCreatedAt(LocalDateTime.now());
        transaction.setTransactionReference(UUID.randomUUID().toString());

        transactionRepository.save(transaction);

        return paymentRepository.save(payment);
    }
    public List<Payment> getUserPayments(Authentication authentication) {

        User user = (User) authentication.getPrincipal();

        return paymentRepository.findByOrder_User(user);
    }

}
