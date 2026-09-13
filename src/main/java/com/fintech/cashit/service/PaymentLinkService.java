package com.fintech.cashit.service;

import com.fintech.cashit.entity.Order;
import com.fintech.cashit.entity.PaymentLink;
import com.fintech.cashit.entity.User;
import com.fintech.cashit.repository.OrderRepository;
import com.fintech.cashit.repository.PaymentLinkRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.UUID;

@Service
public class PaymentLinkService {

    @Autowired
    private PaymentLinkRepository paymentLinkRepository;

    @Autowired
    private OrderRepository orderRepository;

    public PaymentLink createPaymentLink(
            Long orderId,
            Authentication authentication) {

        var user = authentication.getPrincipal();

        Order order = orderRepository
                .findByIdAndUser(orderId, (User) user)
                .orElseThrow(() ->
                        new RuntimeException("Order not found"));

        PaymentLink link = new PaymentLink();

        link.setLinkReference(
                UUID.randomUUID().toString()
        );

        link.setOrder(order);
        link.setActive(true);
        link.setCreatedAt(LocalDateTime.now());

        return paymentLinkRepository.save(link);
    }
}