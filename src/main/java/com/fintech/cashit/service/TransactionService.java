package com.fintech.cashit.service;

import com.fintech.cashit.entity.Transaction;
import com.fintech.cashit.entity.TransactionStatus;
import com.fintech.cashit.entity.User;
import com.fintech.cashit.repository.TransactionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import com.fintech.cashit.entity.TransactionStatus;
import java.time.LocalDateTime;
import java.util.UUID;

import java.time.LocalDateTime;

@Service
public class TransactionService {

    @Autowired
    private TransactionRepository transactionRepository;

    public Transaction createTransaction(Transaction transaction, Authentication authentication) {

        User user = (User) authentication.getPrincipal();

        transaction.setUser(user);
         transaction.setStatus(TransactionStatus.PENDING);
         transaction.setCreatedAt(LocalDateTime.now());
         transaction.setTransactionReference(UUID.randomUUID().toString());
        return transactionRepository.save(transaction);
    }
    }

