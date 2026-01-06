package com.hirewatch.backend.controllers;

import com.hirewatch.backend.models.User;
import com.hirewatch.backend.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/auth")
@CrossOrigin(origins = "http://localhost:5173") // Allows React to access this
public class AuthController {

    @Autowired
    private UserService userService;

    // POST http://localhost:8080/api/auth/register
    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody Map<String, String> payload) {
        try {
            String email = payload.get("email");
            String password = payload.get("password");
            
            User newUser = userService.registerUser(email, password);
            return ResponseEntity.ok(newUser); // Returns the created user (with ID)
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    // POST http://localhost:8080/api/auth/login
    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody Map<String, String> payload) {
        String email = payload.get("email");
        String password = payload.get("password");

        try {
            User user = userService.findByEmail(email);
            
            // Simple password check (In production, use BCrypt here)
            if (user.getPasswordHash().equals(password)) {
                return ResponseEntity.ok(user);
            } else {
                return ResponseEntity.status(401).body("Invalid credentials");
            }
        } catch (RuntimeException e) {
            return ResponseEntity.status(401).body("User not found");
        }
    }
}