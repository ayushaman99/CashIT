package com.fintech.cashit.DTO;

import com.fintech.cashit.entity.TransactionStatus;
import com.fintech.cashit.entity.TransactionType;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class TransactionResponseDTO{

 private Long id;
private BigDecimal amount;

    public Long getId() {
        return id;
    }
    private String currency;
    private Long paymentId;

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public Long getPaymentId() {
        return paymentId;
    }

    public void setPaymentId(Long paymentId) {
        this.paymentId = paymentId;
    }
    public void setId(Long id) {
        this.id = id;
    }

    public TransactionStatus getStatus() {
        return status;
    }

    public void setStatus(TransactionStatus status) {
        this.status = status;
    }

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

    public String getTransactionReference() {
        return transactionReference;
    }

    public void setTransactionReference(String transactionReference) {
        this.transactionReference = transactionReference;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    private TransactionStatus status;
private TransactionType type;
private String description;
private String transactionReference;
private LocalDateTime createdAt;

}
