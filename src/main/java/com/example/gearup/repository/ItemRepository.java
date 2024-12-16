package com.example.gearup.repository;

import com.example.gearup.data.Item;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ItemRepository extends JpaRepository<Item, Integer> {

    List<Item> findByOwner_UserId(Integer userId);

    List<Item> findByNameContainingIgnoreCase(String name);

    @Query(value = "SELECT * FROM Items ORDER BY daily_rental_price ASC", nativeQuery = true)
    List<Item> sortByPriceLowToHigh();

    @Query(value = "SELECT * FROM Items ORDER BY daily_rental_price DESC", nativeQuery = true)
    List<Item> sortByPriceHighToLow();

    @Query(value = "SELECT * FROM Items WHERE ST_Distance_Sphere(item_location, ST_GeomFromText(:point)) <= :distance", nativeQuery = true)
    List<Item> findByLocationRange(String point, double distance);

    // Find items by name and within a location range, sorted by distance
    @Query(value = "SELECT *, ST_Distance_Sphere(item_location, ST_GeomFromText(:point)) AS distance " +
            "FROM Items WHERE name LIKE %:name% " +
            "AND ST_Distance_Sphere(item_location, ST_GeomFromText(:point)) <= :distance " +
            "ORDER BY distance ASC", nativeQuery = true)
    List<Item> findByNameAndLocation(String name, String point, double distance);
}
