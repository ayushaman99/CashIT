package com.fintech.cashit.service;

import com.fintech.cashit.entity.Payment;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
public class FraudCheckService {

    public boolean isFraudulent(Payment payment) {

        if (payment.getAmount().compareTo(new BigDecimal("100000")) > 0) {
            return true;
        }
        if (payment.getAmount().compareTo(new BigDecimal("1")) < 0) {
            return true;
        }

        return false;
    }
}