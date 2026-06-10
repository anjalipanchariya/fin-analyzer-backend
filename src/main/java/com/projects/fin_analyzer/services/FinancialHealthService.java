package com.projects.fin_analyzer.services;

import com.projects.fin_analyzer.dto.FinancialHealthResponse;
import org.springframework.stereotype.Service;

@Service
public interface FinancialHealthService {
    FinancialHealthResponse getFinancialHealth(Long userId);
}
