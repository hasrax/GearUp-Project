package com.example.gearup.repository;

import com.example.gearup.data.SubCategory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface SubCategoryRepository extends JpaRepository<SubCategory, Integer> {

    // Custom query method to find sub-categories by name
    Optional<SubCategory> findBySubName(String subName);

    // Custom query method to find all sub-categories for a specific category
    List<SubCategory> findByCategory_CategoryId(Integer categoryId);
}

