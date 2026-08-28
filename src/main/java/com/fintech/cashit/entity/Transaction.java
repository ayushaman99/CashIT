package com.fintech.cashit.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Positive;

import java.math.BigDecimal;

@Entity
public class Transaction {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Positive
    private BigDecimal amount;

    public Transaction() {
    }

    public Transaction(Long id, BigDecimal amount,TransactionStatus status,TransactionType type, User user) {
        this.id = id;
        this.amount = amount;
        this.status=status;

        this.type=type;
        this.user = user;
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


    public TransactionStatus getStatus() {
        return status;
    }

    public void setStatus(TransactionStatus status) {
        this.status = status;
    }

    public User getUseer() {
        return user;
    }

    public void setUseer(User useer) {
        this.user = useer;
    }



    @Enumerated(EnumType.STRING)
    private TransactionStatus status;


    @Enumerated(EnumType.STRING)
    private TransactionType type;

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public TransactionType getType() {
        return type;
    }

    public void setType(TransactionType type) {
        this.type = type;
    }

    @ManyToOne
    private User user;


}
