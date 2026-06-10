package com.projects.fin_analyzer.dto;

import com.projects.fin_analyzer.enums.Category;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class DashboardResponse {
    private BigDecimal totalIncome;
    private BigDecimal totalExpense;
    private BigDecimal savings;
    private BigDecimal savingsRate;
    private Category topExpenseCategory;
    private Long transactionCount;
}
