package com.hirewatch.backend.repositories;

import com.hirewatch.backend.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    
    // Used for Login: Find a user by their email
    Optional<User> findByEmail(String email);

    // Used for Registration: Check if an email is already taken
    boolean existsByEmail(String email);
}