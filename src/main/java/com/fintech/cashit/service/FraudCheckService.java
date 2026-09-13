package com.fintech.cashit.service;

import com.fintech.cashit.entity.Payment;
import com.fintech.cashit.repository.PaymentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Service
public class FraudCheckService {

    @Autowired
    private PaymentRepository paymentRepository;

    public boolean isFraudulent(Payment payment) {


        if (payment.getAmount().compareTo(new BigDecimal("100000")) > 0) {
            return true;
        }


        if (payment.getAmount().compareTo(new BigDecimal("1")) < 0) {
            return true;
        }


        LocalDateTime oneMinuteAgo =
                LocalDateTime.now().minusMinutes(1);

        long recentPayments =
                paymentRepository.countByOrder_UserAndCreatedAtAfter(
                        payment.getOrder().getUser(),
                        oneMinuteAgo
                );

        if (recentPayments >= 5) {
            return true;
        }

        return false;
    }
}