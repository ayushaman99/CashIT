package com.fintech.cashit.exception;

import com.fintech.cashit.entity.PaymentStatus;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import com.fintech.cashit.exception.PaymentStatusException;

@RestControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(OrderNotFoundException.class)

    public ResponseEntity<String> handleOrderNotFound(OrderNotFoundException ex){
        return ResponseEntity.
                status(HttpStatus.NOT_FOUND)
                .body(ex.getMessage());
    }

    @ExceptionHandler(PaymentNotFoundException.class)

    public ResponseEntity<String> handlePaymentNotFound(PaymentNotFoundException ex){
        return ResponseEntity.
                status(HttpStatus.NOT_FOUND)//http responses
                .body(ex.getMessage());
    }
    @ExceptionHandler(PaymentStatusException.class)
    public ResponseEntity<String> handlePaymentState(
            PaymentStatusException ex) {

        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(ex.getMessage());
    }
}
