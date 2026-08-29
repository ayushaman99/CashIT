package com.fintech.cashit.service;

import com.fintech.cashit.DTO.OrderRequestDTO;
import com.fintech.cashit.entity.Order;
import com.fintech.cashit.entity.OrderStatus;
import com.fintech.cashit.entity.User;
import com.fintech.cashit.exception.OrderNotFoundException;
import com.fintech.cashit.repository.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Service
public class OrderService {

    @Autowired
    private OrderRepository orderRepository;
    public Order createOrder(
            OrderRequestDTO request,
            Authentication authentication) {

        User user = (User) authentication.getPrincipal();

        Order order = new Order();

        order.setAmount(request.getAmount());
        order.setDescription(request.getDescription());

        order.setUser(user);
        order.setStatus(OrderStatus.CREATED);
        order.setCreatedAt(LocalDateTime.now());
        order.setOrderReference(UUID.randomUUID().toString());

        return orderRepository.save(order);
    }
    public Order getOrderById(
            Long id,
            Authentication authentication) {

        User user = (User) authentication.getPrincipal();

        return orderRepository
                .findByIdAndUser(id, user)
                .orElseThrow(()->new OrderNotFoundException("Order Not Found"));
    }
    public List<Order> getUserOrders(Authentication authentication) {

        User user = (User) authentication.getPrincipal();

        return orderRepository.findByUser(user);
    }
}
