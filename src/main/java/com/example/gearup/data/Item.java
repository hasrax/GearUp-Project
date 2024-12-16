package com.example.gearup.data;

import jakarta.persistence.*;
import org.hibernate.annotations.ColumnTransformer;

import java.math.BigDecimal;

@Entity
@Table(name = "Items")
public class Item {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer itemId;

    @Column(nullable = false)
    private String name;

    @ManyToOne
    @JoinColumn(name = "category_id", nullable = true)
    private Category category;

    @ManyToOne
    @JoinColumn(name = "owner_id", nullable = true)
    private UserAuth owner;

    @Column(columnDefinition = "POINT NOT NULL")
    @ColumnTransformer(read = "ST_AsText(item_location)", write = "ST_GeomFromText(?)")
    private String itemLocation; // Store as WKT (Well-Known Text)

    @Column(nullable = false)
    private String district;

    @Column(nullable = false)
    private String city;

    @Column(nullable = false, length = 20)
    private String postalcode;

    @Column(nullable = false, precision = 10, scale = 2)
    private BigDecimal dailyRentalPrice;

    @Column(nullable = false, precision = 10, scale = 2)
    private BigDecimal weeklyRentalPrice;

    @Column(nullable = false, precision = 10, scale = 2)
    private BigDecimal monthlyRentalPrice;

    @Column(columnDefinition = "TEXT")
    private String images;

    @ManyToOne
    @JoinColumn(name = "sub_id", nullable = true)
    private SubCategory subCategory;

    // Getters and Setters

    public Integer getItemId() {
        return itemId;
    }

    public void setItemId(Integer itemId) {
        this.itemId = itemId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public UserAuth getOwner() {
        return owner;
    }

    public void setOwner(UserAuth owner) {
        this.owner = owner;
    }

    public String getItemLocation() {
        return itemLocation;
    }

    public void setItemLocation(String itemLocation) {
        this.itemLocation = itemLocation;
    }

    public String getDistrict() {
        return district;
    }

    public void setDistrict(String district) {
        this.district = district;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getPostalcode() {
        return postalcode;
    }

    public void setPostalcode(String postalcode) {
        this.postalcode = postalcode;
    }

    public BigDecimal getDailyRentalPrice() {
        return dailyRentalPrice;
    }

    public void setDailyRentalPrice(BigDecimal dailyRentalPrice) {
        this.dailyRentalPrice = dailyRentalPrice;
    }

    public BigDecimal getWeeklyRentalPrice() {
        return weeklyRentalPrice;
    }

    public void setWeeklyRentalPrice(BigDecimal weeklyRentalPrice) {
        this.weeklyRentalPrice = weeklyRentalPrice;
    }

    public BigDecimal getMonthlyRentalPrice() {
        return monthlyRentalPrice;
    }

    public void setMonthlyRentalPrice(BigDecimal monthlyRentalPrice) {
        this.monthlyRentalPrice = monthlyRentalPrice;
    }

    public String getImages() {
        return images;
    }

    public void setImages(String images) {
        this.images = images;
    }

    public SubCategory getSubCategory() {
        return subCategory;
    }

    public void setSubCategory(SubCategory subCategory) {
        this.subCategory = subCategory;
    }
}

