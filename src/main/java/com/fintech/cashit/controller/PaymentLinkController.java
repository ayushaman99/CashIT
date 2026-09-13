package com.fintech.cashit.controller;

import com.fintech.cashit.entity.PaymentLink;
import com.fintech.cashit.service.PaymentLinkService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/payment-links")
public class PaymentLinkController {

    @Autowired
    private PaymentLinkService paymentLinkService;

    @PostMapping("/{orderId}")
    public PaymentLink createPaymentLink(
            @PathVariable Long orderId,
            Authentication authentication) {

        return paymentLinkService.createPaymentLink(
                orderId,
                authentication
        );
    }
}