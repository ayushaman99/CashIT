package com.fintech.cashit.DTO;

import java.math.BigDecimal;

public class PaymentRequestDTO {

    private BigDecimal amount;
    private Long orderId;

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public Long getOrderId() {
        return orderId;
    }

    public void setOrderId(Long orderId) {
        this.orderId = orderId;
    }
}