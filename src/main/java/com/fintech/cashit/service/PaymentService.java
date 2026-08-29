package com.fintech.cashit.service;

import com.fintech.cashit.DTO.PaymentResponseDTO;
import com.fintech.cashit.entity.*;
import com.fintech.cashit.repository.OrderRepository;
import com.fintech.cashit.repository.PaymentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fintech.cashit.repository.OrderRepository;
import com.fintech.cashit.repository.PaymentRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.UUID;
import com.fintech.cashit.DTO.PaymentRequestDTO;

@Service
public class PaymentService {
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
                .orElseThrow(() -> new RuntimeException("Payment not found"));

        if (payment.getStatus() != PaymentStatus.PENDING) {
            throw new RuntimeException("Payment cannot be confirmed");
        }

        payment.setStatus(PaymentStatus.SUCCESS);
        Order order=payment.getOrder();
        order.setStatus(OrderStatus.PAID);
        orderRepository.save(order);

        return paymentRepository.save(payment);
    }

}
