package com.projects.fin_analyzer.services;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AiInsightServiceImpl implements AiInsightService{

    private final GeminiService geminiService;

    @Override
    public String generateInsights() {
        String prompt = """
            You are a personal finance advisor.

            Give 4 short financial insights for a user.

            Keep the response concise.
            Use bullet points.
            """;
        return geminiService.generateInsights(prompt);
//        return "Gemini temporarily disabled";
    }
}
