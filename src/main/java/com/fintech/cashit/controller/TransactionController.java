package com.fintech.cashit.controller;

import com.fintech.cashit.DTO.TransactionRequestDTO;
import com.fintech.cashit.DTO.TransactionResponseDTO;
import com.fintech.cashit.entity.Transaction;
import com.fintech.cashit.service.TransactionService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

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
    public List<TransactionResponseDTO> getUserTransactions(
            Authentication authentication) {

        return transactionService
                .getUserTransaction(authentication)
                .stream()
                .map(transactionService::convertToDTO)
                .toList();

    }
    @GetMapping("/transactions/{id}")
    public TransactionResponseDTO getTransactionById(
            @PathVariable Long id,
            Authentication authentication) {

        return transactionService.convertToDTO(
                transactionService.getTransactionById(id, authentication)
        );
    }

}