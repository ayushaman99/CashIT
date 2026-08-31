package com.fintech.cashit.controller;

import com.fintech.cashit.DTO.OrderRequestDTO;
import com.fintech.cashit.entity.Order;
import com.fintech.cashit.service.OrderService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class OrderController {

    @Autowired
    private OrderService orderService;

    @PostMapping("/orders")
    public Order createOrder(
            @RequestBody @Valid OrderRequestDTO request,
            Authentication authentication) {

        return orderService.createOrder(request, authentication);




    }

    @GetMapping("/orders")
    public List<Order> getUserOrders(Authentication authentication) {
        return orderService.getUserOrders(authentication);
    }
    @GetMapping("/orders/{id}")
    public Order getOrderById(
            @PathVariable Long id,
            Authentication authentication) {

        return orderService.getOrderById(id, authentication);
    }
}
