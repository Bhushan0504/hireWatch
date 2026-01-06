package com.hirewatch.backend.repositories;

import com.hirewatch.backend.models.Company;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CompanyRepository extends JpaRepository<Company, Long> {
    // Basic CRUD (Create, Read, Update, Delete) is included automatically.
    // We don't need custom queries here yet.
}