package com.hirewatch.backend.models;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "companies")
public class Company {

    public enum CheckFrequency {
        DAILY, WEEKLY
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String name;

    @Column(nullable = false)
    private String careerUrl;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private CheckFrequency checkFrequency;

    private LocalDateTime createdAt = LocalDateTime.now();

    // Constructors
    public Company() {}

    public Company(String name, String careerUrl, CheckFrequency checkFrequency) {
        this.name = name;
        this.careerUrl = careerUrl;
        this.checkFrequency = checkFrequency;
    }

    // Getters and Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getCareerUrl() { return careerUrl; }
    public void setCareerUrl(String careerUrl) { this.careerUrl = careerUrl; }

    public CheckFrequency getCheckFrequency() { return checkFrequency; }
    public void setCheckFrequency(CheckFrequency checkFrequency) { this.checkFrequency = checkFrequency; }

    public LocalDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }
}