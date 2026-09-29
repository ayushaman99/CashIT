package com.fintech.cashit.DTO;

public record RedisIdempotencyDTO(
        String status,
        Long paymentId
) {
}