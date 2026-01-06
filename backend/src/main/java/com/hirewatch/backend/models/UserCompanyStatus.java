package com.hirewatch.backend.models;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "user_company_status", 
       uniqueConstraints = @UniqueConstraint(columnNames = {"user_id", "company_id"}))
public class UserCompanyStatus {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @ManyToOne
    @JoinColumn(name = "company_id", nullable = false)
    private Company company;

    @Column(name = "last_clicked_at")
    private LocalDateTime lastClickedAt; // Can be null if never clicked

    // Constructors
    public UserCompanyStatus() {}

    public UserCompanyStatus(User user, Company company) {
        this.user = user;
        this.company = company;
        this.lastClickedAt = null; // Default to null
    }

    // Getters and Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public User getUser() { return user; }
    public void setUser(User user) { this.user = user; }

    public Company getCompany() { return company; }
    public void setCompany(Company company) { this.company = company; }

    public LocalDateTime getLastClickedAt() { return lastClickedAt; }
    public void setLastClickedAt(LocalDateTime lastClickedAt) { this.lastClickedAt = lastClickedAt; }
}