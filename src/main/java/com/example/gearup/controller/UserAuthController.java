package com.example.gearup.controller;

import com.example.gearup.data.UserAuth;
import com.example.gearup.service.UserAuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RestController
public class UserAuthController
{
    @Autowired
    private UserAuthService userauthservice;

    @GetMapping(path="/userauths")
    public List<UserAuth> getAllUserAuth()
    {
        return userauthservice.getAllUserAuth();
    }
    @GetMapping(path="/userauths/{userId}")
    public ResponseEntity<UserAuth> getUserAuthById(@PathVariable int userId)
    {
        UserAuth userAuth = userauthservice.getUserAuthById(userId);
        if (userAuth != null) {
            userAuth.setPassword(null); // Hide password
            return ResponseEntity.ok(userAuth);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
    }

    @GetMapping(path="/userauths", params="email")
    public List<UserAuth> getUserAuthByEmail(@PathVariable String email)
    {
        return userauthservice.getUserAuthByEmail(email);
    }

    @PostMapping(path = "/userauths")
    public UserAuth createUserAuth(@RequestBody UserAuth userauth)
    {
        return userauthservice.createUserAuth(userauth);
    }

    @PutMapping(path = "/userauths")
    public UserAuth updateUserAuth(@RequestBody UserAuth userauth)
    {
        return userauthservice.updateUserAuth(userauth);
    }

    @DeleteMapping(path ="/userauths/{userId}")
    public ResponseEntity<Void> deleteUserAuth(@PathVariable int userId) {
        boolean isDeleted = userauthservice.deleteUserAuth(userId);
        if (isDeleted) {
            return ResponseEntity.ok().build();
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    // Add Image from Device
    @PostMapping("/userauths/{userId}/addimage")
    public ResponseEntity<UserAuth> addImageFromDevice(
            @PathVariable Integer userId,
            @RequestParam("image") MultipartFile imageFile) {
        try {
            UserAuth updatedUser = userauthservice.addImageFromDevice(userId, imageFile);
            updatedUser.setPassword(null); // Hide password
            return ResponseEntity.ok(updatedUser);
        } catch (IOException e) {
            // Image upload failed
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        } catch (RuntimeException e) {
            // User not found
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
    }

    //Remove Image
    @DeleteMapping("/userauths/{userId}/removeImage")
    public ResponseEntity<UserAuth> removeImage(@PathVariable Integer userId) {
        try {
            UserAuth updatedUser = userauthservice.removeImage(userId);
            updatedUser.setPassword(null); // Hide password
            return ResponseEntity.ok(updatedUser);
        } catch (RuntimeException e) {
            // User not found
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        } catch (Exception e) {
            // Other errors
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    // Logout endpoint
    @PostMapping("/logout")
    public ResponseEntity<String> logout() {
        // You may perform any necessary cleanup here (e.g., logging, etc.)
        // In a JWT-based system, the client is responsible for removing the JWT token
        return ResponseEntity.ok("Logout successful");
    }
}
