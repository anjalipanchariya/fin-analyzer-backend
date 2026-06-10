package com.projects.fin_analyzer.controller;

import com.projects.fin_analyzer.dto.MonthlyTrendResponse;
import com.projects.fin_analyzer.entity.User;
import com.projects.fin_analyzer.services.CurrentUserService;
import com.projects.fin_analyzer.services.MonthlyTrendService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/monthly-trend")
public class MonthlyTrendController {

    private final MonthlyTrendService monthlyTrendService;
    private final CurrentUserService currentUserService;
    public MonthlyTrendController(MonthlyTrendService monthlyTrendService, CurrentUserService currentUserService) {
        this.monthlyTrendService = monthlyTrendService;
        this.currentUserService = currentUserService;
    }

    @GetMapping
    public List<MonthlyTrendResponse> getMonthlyTrend(){
        User user = currentUserService.getCurrentUser();
        return monthlyTrendService.getMonthlyTrend(user.getId());
    }
}
