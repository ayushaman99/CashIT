package com.fintech.cashit.repository;

import com.fintech.cashit.entity.Payment;
import com.fintech.cashit.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import javax.swing.text.html.Option;
import java.util.List;
import java.util.Optional;

public interface PaymentRepository extends JpaRepository<Payment,Long> {
    Optional<Payment> findByIdAndUser_User(Long id, User user);
    List<Payment> findByOrder_User(User user);
}
