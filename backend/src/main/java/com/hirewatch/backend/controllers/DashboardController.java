package com.hirewatch.backend.controllers;

import com.hirewatch.backend.models.UserCompanyStatus;
import com.hirewatch.backend.services.DashboardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/dashboard")
@CrossOrigin(origins = "http://localhost:5173") // Allows React to access this
public class DashboardController {

    @Autowired
    private DashboardService dashboardService;

    // GET http://localhost:8080/api/dashboard/{userId}
    @GetMapping("/{userId}")
    public ResponseEntity<List<UserCompanyStatus>> getDashboard(@PathVariable Long userId) {
        List<UserCompanyStatus> dashboard = dashboardService.getDashboardForUser(userId);
        return ResponseEntity.ok(dashboard);
    }

    // POST http://localhost:8080/api/dashboard/click
    @PostMapping("/click")
    public ResponseEntity<?> markClicked(@RequestBody Map<String, Long> payload) {
        Long userId = payload.get("userId");
        Long companyId = payload.get("companyId");

        dashboardService.markCompanyClicked(userId, companyId);
        return ResponseEntity.ok("Clicked recorded");
    }
}