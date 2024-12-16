package com.example.gearup.controller;

import com.example.gearup.data.Payment;
import com.example.gearup.service.PaymentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
//@RequestMapping("/api/payments")
public class PaymentController {

    @Autowired
    private PaymentService paymentService;

    // Get all payments
    @GetMapping(path = "/payments")
    public ResponseEntity<List<Payment>> getAllPayments() {
        List<Payment> payments = paymentService.getAllPayments();
        return ResponseEntity.ok(payments);
    }

    // Get payment by ID
    @GetMapping(path = "/payments/{paymentId}")
    public ResponseEntity<Payment> getPaymentById(@PathVariable Integer paymentId) {
        Optional<Payment> payment = paymentService.getPaymentById(paymentId);
        return payment.map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // Get payments by user ID
    @GetMapping(path = "/payments/user/{userId}")
    public ResponseEntity<List<Payment>> getPaymentsByUserId(@PathVariable Integer userId) {
        List<Payment> payments = paymentService.getPaymentsByUserId(userId);
        return ResponseEntity.ok(payments);
    }

    // Create a new payment
    @PostMapping(path = "/payments")
    public ResponseEntity<Payment> createPayment(@RequestBody Payment payment) {
        Payment createdPayment = paymentService.createPayment(payment);
        return ResponseEntity.ok(createdPayment);
    }
}
