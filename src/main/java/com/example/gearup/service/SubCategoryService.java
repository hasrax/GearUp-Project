package com.example.gearup.service;

import com.example.gearup.data.SubCategory;
import com.example.gearup.repository.SubCategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class SubCategoryService {

    @Autowired
    private SubCategoryRepository subCategoryRepository;

    // Get all sub-categories
    public List<SubCategory> getAllSubCategories() {
        return subCategoryRepository.findAll();
    }

    // Get sub-category by ID
    public Optional<SubCategory> getSubCategoryById(Integer id) {
        return subCategoryRepository.findById(id);
    }

    // Get sub-category by Name
    public Optional<SubCategory> getSubCategoryByName(String name) {
        return subCategoryRepository.findBySubName(name);
    }

    // Get all sub-categories for a specific category
    public List<SubCategory> getSubCategoriesByCategoryId(Integer categoryId) {
        return subCategoryRepository.findByCategory_CategoryId(categoryId);
    }

    // Create a new sub-category
    public SubCategory createSubCategory(SubCategory subCategory) {
        return subCategoryRepository.save(subCategory);
    }

    // Update an existing sub-category
    public Optional<SubCategory> updateSubCategory(Integer id, SubCategory updatedSubCategory) {
        return subCategoryRepository.findById(id).map(subCategory -> {
            subCategory.setSubName(updatedSubCategory.getSubName());
            subCategory.setCategory(updatedSubCategory.getCategory());
            return subCategoryRepository.save(subCategory);
        });
    }

    // Delete a sub-category
    public boolean deleteSubCategory(Integer id) {
        if (subCategoryRepository.existsById(id)) {
            subCategoryRepository.deleteById(id);
            return true;
        }
        return false;
    }
}

