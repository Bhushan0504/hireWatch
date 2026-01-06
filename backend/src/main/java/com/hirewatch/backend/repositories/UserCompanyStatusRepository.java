package com.hirewatch.backend.repositories;

import com.hirewatch.backend.models.UserCompanyStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
import java.util.Optional;

public interface UserCompanyStatusRepository extends JpaRepository<UserCompanyStatus, Long> {

    // 1. Get the Dashboard: Find all company statuses for one specific user
    List<UserCompanyStatus> findByUserId(Long userId);

    // 2. Click Logic: Find the specific row for "User X" and "Company Y" so we can update the time
    Optional<UserCompanyStatus> findByUserIdAndCompanyId(Long userId, Long companyId);
}