package com.fintech.cashit.DTO;

import com.fintech.cashit.entity.OrderStatus;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class OrderResponseDTO {
    private Long id;
    private BigDecimal amount;
    private String currency;
    private String description;
    private OrderStatus status;
    private String orderReference;
    private LocalDateTime createdAt;

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public BigDecimal getAmount() { return amount; }
    public void setAmount(BigDecimal amount) { this.amount = amount; }

    public String getCurrency() { return currency; }
    public void setCurrency(String currency) { this.currency = currency; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    public OrderStatus getStatus() { return status; }
    public void setStatus(OrderStatus status) { this.status = status; }

    public String getOrderReference() { return orderReference; }
    public void setOrderReference(String orderReference) {
        this.orderReference = orderReference;
    }

    public LocalDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }
}