package com.fintech.cashit.controller;

import com.fintech.cashit.DTO.OrderRequestDTO;
import com.fintech.cashit.DTO.OrderResponseDTO;
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

    @PostMapping("/CreateOrders")
    public OrderResponseDTO createOrder(
            @RequestBody @Valid OrderRequestDTO request,
            Authentication authentication) {

        return orderService.convertToDTO(
                orderService.createOrder(request, authentication)
        );
    }

    @GetMapping("/orders")
    public List<OrderResponseDTO> getUserOrders(
            Authentication authentication) {

        return orderService.getUserOrders(authentication)
                .stream()
                .map(orderService::convertToDTO)
                .toList();
    }

    @GetMapping("/orders/{id}")
    public OrderResponseDTO getOrderById(
            @PathVariable Long id,
            Authentication authentication) {

        return orderService.convertToDTO(
                orderService.getOrderById(id, authentication)
        );
    }
}