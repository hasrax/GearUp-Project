package com.example.gearup.controller;

import com.example.gearup.data.Reserv;
import com.example.gearup.service.ReservService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
//@RequestMapping("/api/reservations")
public class ReservController {

    @Autowired
    private ReservService reservService;

    // Get all reservations
    @GetMapping(path = "/reservations")
    public ResponseEntity<List<Reserv>> getAllReservations() {
        List<Reserv> reservations = reservService.getAllReservations();
        return ResponseEntity.ok(reservations);
    }

    // Get reservation by ID
    @GetMapping(path = "/reservations/{reservationId}")
    public ResponseEntity<Reserv> getReservationById(@PathVariable Integer reservationId) {
        Optional<Reserv> reservation = reservService.getReservationById(reservationId);
        return reservation.map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // Get reservations by item ID
    @GetMapping(path = "/reservations/item/{itemId}")
    public ResponseEntity<List<Reserv>> getReservationsByItemId(@PathVariable Integer itemId) {
        List<Reserv> reservations = reservService.getReservationsByItemId(itemId);
        return ResponseEntity.ok(reservations);
    }

    // Get reservations by user ID
    @GetMapping(path = "/reservations/user/{userId}")
    public ResponseEntity<List<Reserv>> getReservationsByUserId(@PathVariable Integer userId) {
        List<Reserv> reservations = reservService.getReservationsByUserId(userId);
        return ResponseEntity.ok(reservations);
    }

    // Create a new reservation
    @PostMapping(path = "/reservations")
    public ResponseEntity<Reserv> createReservation(@RequestBody Reserv reservation) {
        Reserv createdReservation = reservService.createReservation(reservation);
        return ResponseEntity.ok(createdReservation);
    }

    // Update an existing reservation
    @PutMapping(path = "/reservations/{reservationId}")
    public ResponseEntity<Reserv> updateReservation(
            @PathVariable Integer reservationId,
            @RequestBody Reserv updatedReservation) {
        Optional<Reserv> reservation = reservService.updateReservation(reservationId, updatedReservation);
        return reservation.map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // Delete a reservation
    @DeleteMapping(path = "/reservations/{reservationId}")
    public ResponseEntity<Void> deleteReservation(@PathVariable Integer reservationId) {
        boolean deleted = reservService.deleteReservation(reservationId);
        if (deleted) {
            return ResponseEntity.ok().build();
        }
        return ResponseEntity.notFound().build();
    }
}

