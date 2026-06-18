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
    public FinancialHealthController(FinancialHealthService financialHealthService) {
        this.financialHealthService = financialHealthService;
    }

    @GetMapping
    public FinancialHealthResponse getFinancialHealth(){
        return financialHealthService.getFinancialHealth();
    }
}
