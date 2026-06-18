package com.projects.fin_analyzer.services;

import org.springframework.stereotype.Service;

@Service
public interface OpenRouterService {
    String generateInsights(String prompt);
}
