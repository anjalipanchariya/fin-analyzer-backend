package com.projects.fin_analyzer.services;

import com.projects.fin_analyzer.dto.MonthlyTrendResponse;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface MonthlyTrendService {
    List<MonthlyTrendResponse> getMonthlyTrend(Long userId);
}
