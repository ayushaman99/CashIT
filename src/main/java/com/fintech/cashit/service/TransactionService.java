package com.fintech.cashit.service;

import com.fintech.cashit.DTO.TransactionRequestDTO;
import com.fintech.cashit.DTO.TransactionResponseDTO;
import com.fintech.cashit.entity.Transaction;
import com.fintech.cashit.entity.TransactionStatus;
import com.fintech.cashit.entity.User;
import com.fintech.cashit.repository.TransactionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import com.fintech.cashit.entity.TransactionStatus;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import java.time.LocalDateTime;

@Service
public class TransactionService {

    @Autowired
    private TransactionRepository transactionRepository;

    public Transaction createTransaction(TransactionRequestDTO request, Authentication authentication) {

        User user = (User) authentication.getPrincipal();

        Transaction transaction=new Transaction();

        transaction.setUser(user);
         transaction.setStatus(TransactionStatus.PENDING);
         transaction.setCreatedAt(LocalDateTime.now());
         transaction.setTransactionReference(UUID.randomUUID().toString());
        transaction.setAmount(request.getAmount());
        transaction.setType(request.getType());
        transaction.setDescription(request.getDescription());
        return transactionRepository.save(transaction);
    }
    public List<Transaction> getUserTransaction(Authentication authentication){
        User user=(User)authentication.getPrincipal();
        return transactionRepository.findByUser(user);
    }
    public Transaction getTransactionById(Long id, Authentication authentication){
        User user=(User) authentication.getPrincipal();
        return transactionRepository
                .findByUserAndId(id,user)
                .orElse(null);

    }
    public TransactionResponseDTO convertToDTO(Transaction transaction) {

        TransactionResponseDTO dto = new TransactionResponseDTO();

        dto.setId(transaction.getId());
        dto.setAmount(transaction.getAmount());
        dto.setStatus(transaction.getStatus());
        dto.setType(transaction.getType());
        dto.setDescription(transaction.getDescription());
        dto.setTransactionReference(transaction.getTransactionReference());
        dto.setCreatedAt(transaction.getCreatedAt());

        return dto;
    }
    }

