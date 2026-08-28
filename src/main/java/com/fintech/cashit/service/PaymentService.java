package com.fintech.cashit.service;

import com.fintech.cashit.repository.OrderRepository;
import com.fintech.cashit.repository.PaymentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fintech.cashit.entity.Payment;
import com.fintech.cashit.entity.PaymentStatus;
import com.fintech.cashit.entity.Order;
import com.fintech.cashit.entity.User;
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
    public Payment confirmPayment(
            Long paymentId,
            Authentication authentication) {

        User user = (User) authentication.getPrincipal();

        Payment payment = paymentRepository
                .findByIdAndUser_User(paymentId, user)
                .orElseThrow(() -> new RuntimeException("Payment not found"));

        payment.setStatus(PaymentStatus.SUCCESS);

        return paymentRepository.save(payment);
    }

}
