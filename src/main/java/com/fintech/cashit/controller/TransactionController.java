package com.fintech.cashit.controller;

import com.fintech.cashit.DTO.TransactionRequestDTO;
import com.fintech.cashit.entity.Transaction;
import com.fintech.cashit.service.TransactionService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class TransactionController {

    @Autowired
    private TransactionService transactionService;

    @PostMapping("/transactions")
    public Transaction createTransaction(
            @Valid @RequestBody TransactionRequestDTO request,
            Authentication authentication) {

        return transactionService.createTransaction(request, authentication);
    }
    @GetMapping("/transactions")
    public List<Transaction> getUserTransactions(Authentication authentication){
        return transactionService.getUserTransaction(authentication);
    }
}