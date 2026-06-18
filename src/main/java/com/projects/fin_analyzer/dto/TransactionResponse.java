package com.projects.fin_analyzer.dto;

import com.projects.fin_analyzer.enums.*;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;

@Data
public class TransactionResponse {
    private Long transactionId;
    private BigDecimal amount;
    private String description;
    private TransactionType transactionType;
    private Category category;
    private LocalDate transactionDate;
}
