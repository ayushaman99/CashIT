package com.fintech.cashit.exception;

import com.fintech.cashit.entity.PaymentStatus;

public class PaymentStatusException extends RuntimeException{
    public PaymentStatusException (String message){
        super(message);
    }
}
