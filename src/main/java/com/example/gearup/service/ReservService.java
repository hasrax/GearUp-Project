package com.example.gearup.service;

import com.example.gearup.data.Reserv;
import com.example.gearup.repository.ReservRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ReservService {

    @Autowired
    private ReservRepository reservRepository;

    // Get all reservations
    public List<Reserv> getAllReservations() {
        return reservRepository.findAll();
    }

    // Get reservation by ID
    public Optional<Reserv> getReservationById(Integer reservationId) {
        return reservRepository.findById(reservationId);
    }

    // Get reservations by item ID
    public List<Reserv> getReservationsByItemId(Integer itemId) {
        return reservRepository.findByItem_ItemId(itemId);
    }

    // Get reservations by user ID
    public List<Reserv> getReservationsByUserId(Integer userId) {
        return reservRepository.findByUser_UserId(userId);
    }

    // Create a new reservation
    public Reserv createReservation(Reserv reservation) {
        return reservRepository.save(reservation);
    }

    // Update an existing reservation
    public Optional<Reserv> updateReservation(Integer reservationId, Reserv updatedReservation) {
        return reservRepository.findById(reservationId).map(reservation -> {
            reservation.setStartDate(updatedReservation.getStartDate());
            reservation.setEndDate(updatedReservation.getEndDate());
            reservation.setTotalPrice(updatedReservation.getTotalPrice());
            reservation.setStatus(updatedReservation.getStatus());
            reservation.setItem(updatedReservation.getItem());
            reservation.setUser(updatedReservation.getUser());
            return reservRepository.save(reservation);
        });
    }

    // Delete a reservation
    public boolean deleteReservation(Integer reservationId) {
        if (reservRepository.existsById(reservationId)) {
            reservRepository.deleteById(reservationId);
            return true;
        }
        return false;
    }
}

