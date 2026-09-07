package com.fintech.cashit.repository;

import com.fintech.cashit.entity.Idempotency;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface IdempotencyRepository extends JpaRepository<Idempotency, Long> {

    Optional<Idempotency> findByIdempotencyKey(String idempotencyKey);
}