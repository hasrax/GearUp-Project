package com.example.gearup.controller;

import com.example.gearup.data.Promotion;
import com.example.gearup.service.PromotionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
//@RequestMapping("/gear-up")
public class PromotionController {

    @Autowired
    private PromotionService promotionsService;

    // Get all promotions
    @GetMapping(path = "/promotions")
    public ResponseEntity<List<Promotion>> getAllPromotions() {
        List<Promotion> promotions = promotionsService.getAllPromotions();
        return ResponseEntity.ok(promotions);
    }

    // Get promotion by ID
    @GetMapping(path = "/promotions/{id}")
    public ResponseEntity<Promotion> getPromotionById(@PathVariable Integer id) {
        Optional<Promotion> promotion = promotionsService.getPromotionById(id);
        return promotion.map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // Get promotion by discount code
    @GetMapping(path = "/promotions",params = "code")
    public ResponseEntity<Promotion> getPromotionByDiscountCode(@RequestParam String code) {
        Optional<Promotion> promotion = promotionsService.getPromotionByDiscountCode(code);
        return promotion.map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // Create a new promotion
    @PostMapping(path = "/promotions")
    public ResponseEntity<Promotion> createPromotion(@RequestBody Promotion promotion) {
        Promotion createdPromotion = promotionsService.createPromotion(promotion);
        return ResponseEntity.ok(createdPromotion);
    }

    // Update a promotion
    @PutMapping(path = "/promotions/{id}")
    public ResponseEntity<Promotion> updatePromotion(
            @PathVariable Integer id,
            @RequestBody Promotion updatedPromotion) {
        Optional<Promotion> promotion = promotionsService.updatePromotion(id, updatedPromotion);
        return promotion.map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // Delete a promotion
    @DeleteMapping(path = "/promotions/{id}")
    public ResponseEntity<Void> deletePromotion(@PathVariable Integer id) {
        boolean deleted = promotionsService.deletePromotion(id);
        if (deleted) {
            return ResponseEntity.ok().build();
        }
        return ResponseEntity.notFound().build();
    }
}
