package com.projects.fin_analyzer.dto;

import com.projects.fin_analyzer.enums.*;
import jakarta.validation.constraints.*;
import lombok.Data;
import java.math.BigDecimal;

@Data
public class TransactionRequest {
    @NotNull
    private Long userId;

    @NotNull
    private BigDecimal amount;

    @NotBlank
    private String description;

    @NotNull
    private TransactionType transactionType;

    @NotNull
    private Category category;
}
