package com.projects.fin_analyzer.dto;

import com.projects.fin_analyzer.enums.Category;
import lombok.Data;
import java.math.BigDecimal;

@Data
public class CategoryBreakdownResponse {
    private Category category;
    private BigDecimal amount;
}
