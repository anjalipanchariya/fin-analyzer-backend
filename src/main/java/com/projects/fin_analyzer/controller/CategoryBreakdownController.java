package com.projects.fin_analyzer.controller;

import com.projects.fin_analyzer.dto.CategoryBreakdownResponse;
import com.projects.fin_analyzer.entity.User;
import com.projects.fin_analyzer.repository.UserRepository;
import com.projects.fin_analyzer.services.CategoryBreakdownService;
import com.projects.fin_analyzer.services.CurrentUserService;
import org.jspecify.annotations.Nullable;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/category-breakdown")
public class CategoryBreakdownController {

    private final CategoryBreakdownService categoryBreakdownService;
    public CategoryBreakdownController(CategoryBreakdownService categoryBreakdownService, UserRepository userRepository) {
        this.categoryBreakdownService = categoryBreakdownService;
    }

    @GetMapping
    public List<CategoryBreakdownResponse> getCategoryBreakdown(){
        return categoryBreakdownService.getCategoryBreakdown();
    }
}
