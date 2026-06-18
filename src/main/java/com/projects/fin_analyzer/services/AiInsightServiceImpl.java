package com.projects.fin_analyzer.services;

import com.projects.fin_analyzer.dto.DashboardResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AiInsightServiceImpl implements AiInsightService{

    private final OpenRouterService openRouterService;
   private final DashboardService dashboardService;

    @Override
    public String generateInsights() {
        DashboardResponse dashboardResponse = dashboardService.getDashboard();

        String prompt = """
        You are a personal finance advisor.

        User Summary:

        Total Income: ₹%s
        Total Expense: ₹%s
        Savings: ₹%s
        Top Expense Category: %s

        Generate:
        - 4 short personalized insights
        - 1 practical recommendation
        - Do NOT use ** or # symbols.

        Use bullet points.
        Keep the response under 100 words.
        """
                .formatted(
                        dashboardResponse.getTotalIncome(),
                        dashboardResponse.getTotalExpense(),
                        dashboardResponse.getSavings(),
                        dashboardResponse.getTopExpenseCategory()
                );

        return openRouterService.generateInsights(prompt);
    }
}
