package com.hirewatch.backend.services;

import com.hirewatch.backend.models.*;
import com.hirewatch.backend.repositories.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private CompanyRepository companyRepository;

    @Autowired
    private UserCompanyStatusRepository statusRepository;

    public User registerUser(String email, String password) {
        // 1. Check if user already exists
        if (userRepository.existsByEmail(email)) {
            throw new RuntimeException("Email already taken");
        }

        // 2. Create and Save the new User
        // (Note: In a real app, you would hash the password here using BCrypt)
        User newUser = new User(email, password);
        User savedUser = userRepository.save(newUser);

        // 3. AUTOMAGICALLY create the dashboard rows for this user
        List<Company> allCompanies = companyRepository.findAll();

        for (Company company : allCompanies) {
            UserCompanyStatus status = new UserCompanyStatus(savedUser, company);
            // lastClickedAt is null by default in the constructor we made
            statusRepository.save(status);
        }

        return savedUser;
    }
    
    // Helper to find a user (for login later)
    public User findByEmail(String email) {
        return userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));
    }
}