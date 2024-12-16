package com.example.gearup.service;

import com.example.gearup.data.Promotion;
import com.example.gearup.repository.PromotionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PromotionService {

    @Autowired
    private PromotionRepository promotionRepository;

    // Get all promotions
    public List<Promotion> getAllPromotions() {
        return promotionRepository.findAll();
    }

    // Get promotion by ID
    public Optional<Promotion> getPromotionById(Integer id) {
        return promotionRepository.findById(id);
    }

    // Get promotion by discount code
    public Optional<Promotion> getPromotionByDiscountCode(String discountCode) {
        return promotionRepository.findByDiscountCode(discountCode);
    }

    // Create a new promotion
    public Promotion createPromotion(Promotion promotion) {
        return promotionRepository.save(promotion);
    }

    // Update an existing promotion
    public Optional<Promotion> updatePromotion(Integer id, Promotion updatedPromotion) {
        return promotionRepository.findById(id).map(promotion -> {
            promotion.setDiscountCode(updatedPromotion.getDiscountCode());
            promotion.setDiscountPercentage(updatedPromotion.getDiscountPercentage());
            promotion.setExpirationDate(updatedPromotion.getExpirationDate());
            return promotionRepository.save(promotion);
        });
    }

    // Delete a promotion
    public boolean deletePromotion(Integer id) {
        if (promotionRepository.existsById(id)) {
            promotionRepository.deleteById(id);
            return true;
        }
        return false;
    }
}

