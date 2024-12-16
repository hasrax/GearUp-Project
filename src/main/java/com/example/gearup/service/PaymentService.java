package com.example.gearup.service;

import com.example.gearup.data.Payment;
import com.example.gearup.repository.PaymentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PaymentService {

    @Autowired
    private PaymentRepository paymentRepository;

    // Get all payments
    public List<Payment> getAllPayments() {
        return paymentRepository.findAll();
    }

    // Get payment by ID
    public Optional<Payment> getPaymentById(Integer paymentId) {
        return paymentRepository.findById(paymentId);
    }

    // Get payments by user ID
    public List<Payment> getPaymentsByUserId(Integer userId) {
        return paymentRepository.findByUser_UserId(userId);
    }

    // Create a new payment
    public Payment createPayment(Payment payment) {
        return paymentRepository.save(payment);
    }
}

