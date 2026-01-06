package com.hirewatch.backend.services;

import com.hirewatch.backend.models.UserCompanyStatus;
import com.hirewatch.backend.repositories.UserCompanyStatusRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class DashboardService {

    @Autowired
    private UserCompanyStatusRepository statusRepository;

    // Get the full list for the frontend (Red/Green buttons)
    public List<UserCompanyStatus> getDashboardForUser(Long userId) {
        return statusRepository.findByUserId(userId);
    }

    // The logic when a user clicks a button
    public void markCompanyClicked(Long userId, Long companyId) {
        UserCompanyStatus status = statusRepository.findByUserIdAndCompanyId(userId, companyId)
                .orElseThrow(() -> new RuntimeException("Mapping not found"));

        // Update the time to NOW
        status.setLastClickedAt(LocalDateTime.now());
        
        statusRepository.save(status);
    }
}