package com.projects.fin_analyzer.services;

import org.springframework.stereotype.Service;

@Service
public interface GeminiService {
    String generateInsights(String prompt);
}
