package com.fintech.cashit.controller;

import com.fintech.cashit.DTO.PaymentRequestDTO;
import com.fintech.cashit.DTO.PaymentResponseDTO;
import com.fintech.cashit.entity.Payment;
import com.fintech.cashit.service.PaymentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class PaymentController {
    @Autowired
    private PaymentService paymentService;

    @PostMapping("/payments")
    public PaymentResponseDTO createPayment(
            @RequestBody PaymentRequestDTO request,
            Authentication authentication) {

        return paymentService.convertToDTO(
                paymentService.createPayment(request, authentication)
        );
    }
    @PutMapping("/payments/{id}/confirm")
    public PaymentResponseDTO confirmPayment(
            @PathVariable Long id,
            Authentication authentication) {

        return paymentService.convertToDTO(
                paymentService.confirmPayment(id, authentication)
        );
    }
    @GetMapping("/payments")
    public List<PaymentResponseDTO> getUserPayments(
            Authentication authentication) {

        return paymentService
                .getUserPayments(authentication)
                .stream()
                .map(payment -> paymentService.convertToDTO(payment))
                .toList();
    }
}
