package com.projects.fin_analyzer.dto;

import lombok.Data;
import org.apache.kafka.common.protocol.types.Field;

import java.math.BigDecimal;

@Data
public class FinancialHealthResponse {
    private BigDecimal savingsRate;
    private String status;
    private Integer score;
}
