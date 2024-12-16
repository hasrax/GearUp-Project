package com.example.gearup.repository;

import com.example.gearup.data.Reserv;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ReservRepository extends JpaRepository<Reserv, Integer> {

    // Find all reservations by item ID
    List<Reserv> findByItem_ItemId(Integer itemId);

    // Find all reservations by user ID
    List<Reserv> findByUser_UserId(Integer userId);
}

