package com.fintech.cashit.repository;

import com.fintech.cashit.entity.PaymentLink;
import com.fintech.cashit.entity.Order;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface PaymentLinkRepository
        extends JpaRepository<PaymentLink, Long> {

    Optional<PaymentLink> findByLinkReference(String linkReference);

    Optional<PaymentLink> findByOrder(Order order);
}