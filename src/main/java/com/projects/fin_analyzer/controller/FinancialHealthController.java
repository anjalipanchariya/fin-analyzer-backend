package com.projects.fin_analyzer.controller;

import com.projects.fin_analyzer.dto.FinancialHealthResponse;
import com.projects.fin_analyzer.entity.User;
import com.projects.fin_analyzer.services.CurrentUserService;
import com.projects.fin_analyzer.services.FinancialHealthService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/financial-health")
public class FinancialHealthController {
    private final FinancialHealthService financialHealthService;
    private final CurrentUserService currentUserService;
    public FinancialHealthController(FinancialHealthService financialHealthService, CurrentUserService currentUserService) {
        this.financialHealthService = financialHealthService;
        this.currentUserService = currentUserService;
    }

    @GetMapping
    public FinancialHealthResponse getFinancialHealth(){
        User user = currentUserService.getCurrentUser();
        return financialHealthService.getFinancialHealth(user.getId());
    }
}
