package com.example.gearup.data;

import jakarta.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "Promotions_and_Discounts")
public class Promotion {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer promotionId;

    @Column(nullable = false, unique = true, length = 50)
    private String discountCode;

    @Column(nullable = false, precision = 5, scale = 2)
    private Double discountPercentage;

    @Column(nullable = false)
    private LocalDate expirationDate;

    // Getters and Setters
    public Integer getPromotionId() {
        return promotionId;
    }

    public void setPromotionId(Integer promotionId) {
        this.promotionId = promotionId;
    }

    public String getDiscountCode() {
        return discountCode;
    }

    public void setDiscountCode(String discountCode) {
        this.discountCode = discountCode;
    }

    public Double getDiscountPercentage() {
        return discountPercentage;
    }

    public void setDiscountPercentage(Double discountPercentage) {
        this.discountPercentage = discountPercentage;
    }

    public LocalDate getExpirationDate() {
        return expirationDate;
    }

    public void setExpirationDate(LocalDate expirationDate) {
        this.expirationDate = expirationDate;
    }
}

