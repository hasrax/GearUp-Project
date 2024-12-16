package com.example.gearup.repository;

import com.example.gearup.data.Promotion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PromotionRepository extends JpaRepository<Promotion, Integer> {

    // Custom query method to find promotion by discount code
    Optional<Promotion> findByDiscountCode(String discountCode);
}

