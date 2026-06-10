package com.projects.fin_analyzer.services;

import com.projects.fin_analyzer.dto.DashboardResponse;
import org.springframework.stereotype.Service;

@Service
public interface DashboardService {
    DashboardResponse getDashboard(Long userId);
}
