package com.fintech.cashit.service;

import com.fintech.cashit.entity.Order;
import com.fintech.cashit.entity.Payment;
import com.fintech.cashit.entity.User;
import com.fintech.cashit.repository.PaymentRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class FraudCheckServiceTest {

    @Mock
    private PaymentRepository paymentRepository;

    @InjectMocks
    private FraudCheckService fraudCheckService;

    private Payment payment;

    @BeforeEach
    void setUp() {

        User user = new User();

        Order order = new Order();
        order.setUser(user);

        payment = new Payment();
        payment.setAmount(new BigDecimal("100"));
        payment.setOrder(order);

        when(paymentRepository
                .countByOrder_UserAndCreatedAtAfter(
                        any(User.class),
                        any()))
                .thenReturn(0L);
    }

    @Test
    void shouldBlockLargePayment() {

        payment.setAmount(new BigDecimal("100001"));

        assertTrue(
                fraudCheckService.isFraudulent(payment)
        );
    }

    @Test
    void shouldBlockTinyPayment() {

        payment.setAmount(new BigDecimal("0.50"));

        assertTrue(
                fraudCheckService.isFraudulent(payment)
        );
    }

    @Test
    void shouldAllowNormalPayment() {

        payment.setAmount(new BigDecimal("100"));

        assertFalse(
                fraudCheckService.isFraudulent(payment)
        );
    }

    @Test
    void shouldBlockTooManyRecentPayments() {

        when(paymentRepository
                .countByOrder_UserAndCreatedAtAfter(
                        any(User.class),
                        any()))
                .thenReturn(5L);

        assertTrue(
                fraudCheckService.isFraudulent(payment)
        );
    }
}