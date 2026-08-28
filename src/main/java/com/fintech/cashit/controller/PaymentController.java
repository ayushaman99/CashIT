package com.fintech.cashit.controller;

import com.fintech.cashit.DTO.PaymentRequestDTO;
import com.fintech.cashit.entity.Payment;
import com.fintech.cashit.service.PaymentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
public class PaymentController {
    @Autowired
    private PaymentService paymentService;

    @PostMapping("/payments")
    public Payment createPayment(
            @RequestBody PaymentRequestDTO request,
            Authentication authentication) {

        return paymentService.createPayment(request, authentication);
    }
    @PutMapping("/payments/{id}/confirm")
    public Payment confirmPayment(
            @PathVariable Long id,
            Authentication authentication) {

        return paymentService.confirmPayment(id, authentication);
    }
}
