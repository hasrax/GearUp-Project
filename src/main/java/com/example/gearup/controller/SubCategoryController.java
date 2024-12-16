package com.example.gearup.controller;

import com.example.gearup.data.SubCategory;
import com.example.gearup.service.SubCategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
//@RequestMapping("/api/subcategories")
public class SubCategoryController {

    @Autowired
    private SubCategoryService subCategoryService;

    // Get all sub-categories
    @GetMapping(path = "/subcategories")
    public ResponseEntity<List<SubCategory>> getAllSubCategories() {
        List<SubCategory> subCategories = subCategoryService.getAllSubCategories();
        return ResponseEntity.ok(subCategories);
    }

    // Get sub-category by ID
    @GetMapping(path = "/subcategories/{id}")
    public ResponseEntity<SubCategory> getSubCategoryById(@PathVariable Integer id) {
        Optional<SubCategory> subCategory = subCategoryService.getSubCategoryById(id);
        return subCategory.map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // Get sub-category by Name
    @GetMapping(path = "/subcategories", params = "name")
    public ResponseEntity<SubCategory> getSubCategoryByName(@RequestParam String name) {
        Optional<SubCategory> subCategory = subCategoryService.getSubCategoryByName(name);
        return subCategory.map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // Get all sub-categories for a specific category
    @GetMapping(path = "/subcategories/category/{categoryId}")
    public ResponseEntity<List<SubCategory>> getSubCategoriesByCategoryId(@PathVariable Integer categoryId) {
        List<SubCategory> subCategories = subCategoryService.getSubCategoriesByCategoryId(categoryId);
        return ResponseEntity.ok(subCategories);
    }

    // Create a new sub-category
    @PostMapping(path = "/subcategories")
    public ResponseEntity<SubCategory> createSubCategory(@RequestBody SubCategory subCategory) {
        SubCategory createdSubCategory = subCategoryService.createSubCategory(subCategory);
        return ResponseEntity.ok(createdSubCategory);
    }

    // Update a sub-category
    @PutMapping(path = "/subcategories")
    public ResponseEntity<SubCategory> updateSubCategory(
            @PathVariable Integer id,
            @RequestBody SubCategory updatedSubCategory) {
        Optional<SubCategory> subCategory = subCategoryService.updateSubCategory(id, updatedSubCategory);
        return subCategory.map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // Delete a sub-category
    @DeleteMapping(path = "/subcategories/{id}")
    public ResponseEntity<Void> deleteSubCategory(@PathVariable Integer id) {
        boolean deleted = subCategoryService.deleteSubCategory(id);
        if (deleted) {
            return ResponseEntity.ok().build();
        }
        return ResponseEntity.notFound().build();
    }
}
