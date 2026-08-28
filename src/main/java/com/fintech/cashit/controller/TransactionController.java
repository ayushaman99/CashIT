package com.fintech.cashit.controller;

import com.fintech.cashit.entity.Transaction;
import com.fintech.cashit.service.TransactionService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TransactionController {

    @Autowired
    private TransactionService transactionService;

    @PostMapping("/transactions")
    public Transaction createTransaction(
           @Valid @RequestBody Transaction transaction,
            Authentication authentication) {

        return transactionService.createTransaction(transaction, authentication);
    }
}