package com.projects.fin_analyzer.services;

import com.projects.fin_analyzer.dto.DashboardResponse;
import com.projects.fin_analyzer.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

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
