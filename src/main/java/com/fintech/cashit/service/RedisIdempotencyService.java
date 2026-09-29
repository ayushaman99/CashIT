package com.fintech.cashit.service;

import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.time.Duration;

@Service
public class RedisIdempotencyService {

    private final RedisTemplate<String, String> redisTemplate;

    public RedisIdempotencyService(RedisTemplate<String, String> redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    public void save(String key, String value, Duration ttl) {
        redisTemplate.opsForValue().set(key, value, ttl);
    }

    public String get(String key) {
        return redisTemplate.opsForValue().get(key);
    }

    public void delete(String key) {
        redisTemplate.delete(key);
    }
    public void savePaymentId(String idempotencyKey, Long paymentId) {

        String key = "idempotency:" + idempotencyKey;

        redisTemplate.opsForValue()
                .set(key, paymentId.toString(), Duration.ofHours(24));
    }

    public boolean markProcessing(String idempotencyKey) {

        String key = "idempotency:" + idempotencyKey;

        return redisTemplate.opsForValue()
                .setIfAbsent(
                        key,
                        "PROCESSING",
                        Duration.ofMinutes(10)
                );
    }

    public Long getPaymentId(String idempotencyKey) {

        String key = "idempotency:" + idempotencyKey;

        String value = redisTemplate.opsForValue().get(key);

        if (value == null) {
            return null;
        }

        return Long.valueOf(value);
    }
}