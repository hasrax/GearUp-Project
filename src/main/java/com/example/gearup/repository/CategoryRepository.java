package com.example.gearup.repository;

import com.example.gearup.data.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Integer>
{

    // Custom query method to find category by name
    Optional<Category> findByName(String name);
}
