package com.projects.fin_analyzer.dto;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class MonthlyTrendResponse {
    String month;
    BigDecimal expense;
}
