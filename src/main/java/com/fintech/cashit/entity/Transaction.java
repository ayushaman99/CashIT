package com.fintech.cashit.entity;

import jakarta.persistence.*;

import java.math.BigDecimal;

@Entity
public class Transaction {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private BigDecimal amount;

    public Transaction() {
    }

    public Transaction(Long id, BigDecimal amount, String status, String type, User useer) {
        this.id = id;
        this.amount = amount;
        this.status = status;
        this.type = type;
        this.useer = useer;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public User getUseer() {
        return useer;
    }

    public void setUseer(User useer) {
        this.useer = useer;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    private String status;
    private String type;
    @ManyToOne
    private User useer;


}
