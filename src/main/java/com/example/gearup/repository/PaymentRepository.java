package com.example.gearup.repository;

import com.example.gearup.data.Payment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PaymentRepository extends JpaRepository<Payment, Integer> {

    // Find payments by user ID
    List<Payment> findByUser_UserId(Integer userId);
}

