package com.fintech.cashit.DTO;

import com.fintech.cashit.entity.TransactionType;

import java.math.BigDecimal;

public class TransactionRequestDTO {
    private BigDecimal amount;
    private String description;
    private TransactionType type;

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public TransactionType getType() {
        return type;
    }

    public void setType(TransactionType type) {
        this.type = type;
    }
}
