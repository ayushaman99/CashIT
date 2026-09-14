package com.fintech.cashit.service;

import com.fintech.cashit.DTO.OrderRequestDTO;
import com.fintech.cashit.entity.Order;
import com.fintech.cashit.entity.OrderStatus;
import com.fintech.cashit.entity.User;
import com.fintech.cashit.exception.OrderNotFoundException;
import com.fintech.cashit.repository.OrderRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.Authentication;

import java.math.BigDecimal;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class OrderServiceTest {

    @Mock
    private OrderRepository orderRepository;

    @Mock
    private Authentication authentication;

    @InjectMocks
    private OrderService orderService;

    private User user;

    @BeforeEach
    void setUp() {
        user = new User();

        when(authentication.getPrincipal())
                .thenReturn(user);
    }

    @Test
    void shouldCreateOrder() {

        OrderRequestDTO request = new OrderRequestDTO();

        request.setAmount(new BigDecimal("500"));
        request.setCurrency("INR");
        request.setDescription("Test order");

        Order savedOrder = new Order();

        when(orderRepository.save(any(Order.class)))
                .thenReturn(savedOrder);

        Order result = orderService.createOrder(
                request,
                authentication
        );

        assertNotNull(result);

        verify(orderRepository).save(any(Order.class));
    }

    @Test
    void shouldNormalizeCurrency() {

        OrderRequestDTO request = new OrderRequestDTO();

        request.setAmount(new BigDecimal("500"));
        request.setCurrency("inr");
        request.setDescription("Test order");

        when(orderRepository.save(any(Order.class)))
                .thenAnswer(invocation -> invocation.getArgument(0));

        Order result = orderService.createOrder(
                request,
                authentication
        );

        assertEquals("INR", result.getCurrency());
        assertEquals(new BigDecimal("500"), result.getAmount());
        assertEquals("Test order", result.getDescription());
        assertEquals(OrderStatus.CREATED, result.getStatus());
        assertEquals(user, result.getUser());

        assertNotNull(result.getOrderReference());
        assertNotNull(result.getCreatedAt());
    }

    @Test
    void shouldRejectUnsupportedCurrency() {

        OrderRequestDTO request = new OrderRequestDTO();

        request.setAmount(new BigDecimal("500"));
        request.setCurrency("XYZ");
        request.setDescription("Test order");

        assertThrows(
                IllegalArgumentException.class,
                () -> orderService.createOrder(
                        request,
                        authentication
                )
        );

        verify(orderRepository, never())
                .save(any(Order.class));
    }

    @Test
    void shouldGetOrderById() {

        Order order = new Order();
        order.setUser(user);

        when(orderRepository.findByIdAndUser(1L, user))
                .thenReturn(Optional.of(order));

        Order result = orderService.getOrderById(
                1L,
                authentication
        );

        assertNotNull(result);
        assertEquals(order, result);

        verify(orderRepository)
                .findByIdAndUser(1L, user);
    }

    @Test
    void shouldThrowExceptionWhenOrderNotFound() {

        when(orderRepository.findByIdAndUser(1L, user))
                .thenReturn(Optional.empty());

        assertThrows(
                OrderNotFoundException.class,
                () -> orderService.getOrderById(
                        1L,
                        authentication
                )
        );
    }
}