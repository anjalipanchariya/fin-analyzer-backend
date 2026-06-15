package com.projects.fin_analyzer.controller;

import com.projects.fin_analyzer.dto.AiInsightsResponse;
import com.projects.fin_analyzer.services.AiInsightService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AiInsightController {

    private final AiInsightService aiInsightService;

    public AiInsightController(AiInsightService aiInsightService) {
        this.aiInsightService = aiInsightService;
    }

    @GetMapping("/ai-insights")
    public AiInsightsResponse getInsights(){
        System.out.println("AI endpoint hit");
        AiInsightsResponse aiInsightsResponse = new AiInsightsResponse();
        aiInsightsResponse.setInsights(aiInsightService.generateInsights());
        return aiInsightsResponse;
    }
}
