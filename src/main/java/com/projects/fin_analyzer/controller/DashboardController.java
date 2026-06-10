package com.projects.fin_analyzer.controller;

import com.projects.fin_analyzer.dto.DashboardResponse;
import com.projects.fin_analyzer.entity.User;
import com.projects.fin_analyzer.services.CurrentUserService;
import com.projects.fin_analyzer.services.DashboardService;
import org.springframework.web.bind.annotation.*;

import java.util.Currency;

@RestController
@RequestMapping("dashboard")
public class DashboardController {

    private final DashboardService dashboardService;
    private final CurrentUserService currentUserService;
    public DashboardController(DashboardService dashboardService, CurrentUserService currentUserService) {
        this.dashboardService = dashboardService;
        this.currentUserService = currentUserService;
    }

    @GetMapping
    public DashboardResponse getDashboard(){
        User user = currentUserService.getCurrentUser();
        return dashboardService.getDashboard(user.getId());
    }
}
