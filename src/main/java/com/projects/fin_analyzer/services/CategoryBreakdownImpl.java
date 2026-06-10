package com.projects.fin_analyzer.services;

import com.projects.fin_analyzer.dto.CategoryBreakdownResponse;
import com.projects.fin_analyzer.entity.Transaction;
import com.projects.fin_analyzer.enums.Category;
import com.projects.fin_analyzer.enums.TransactionType;
import com.projects.fin_analyzer.repository.TransactionRepository;
import com.projects.fin_analyzer.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class CategoryBreakdownImpl implements CategoryBreakdownService {

    private final UserRepository userRepository;
    private final TransactionRepository transactionRepository;

    public CategoryBreakdownImpl(UserRepository userRepository, TransactionRepository transactionRepository) {
        this.userRepository = userRepository;
        this.transactionRepository = transactionRepository;
    }

    @Override
    public List<CategoryBreakdownResponse> getCategoryBreakdown(Long userId) {

        userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        List<Transaction> transactionList = transactionRepository.findByUserId(userId);
        Map<Category, BigDecimal> categoryMap = new HashMap<>();

        for(Transaction transaction : transactionList){
            if(transaction.getType() == TransactionType.EXPENSE){
                Category category = transaction.getCategory();
                categoryMap.put(category, categoryMap.getOrDefault(category,BigDecimal.ZERO).add(transaction.getAmount()));
            }
        }

        List<CategoryBreakdownResponse> response = new ArrayList<>();
        for(Map.Entry<Category,BigDecimal> entry : categoryMap.entrySet()){
            CategoryBreakdownResponse categoryBreakdownResponse = new CategoryBreakdownResponse();
            categoryBreakdownResponse.setCategory(entry.getKey());
            categoryBreakdownResponse.setAmount(entry.getValue());
            response.add(categoryBreakdownResponse);
        }
        return response;
    }
}
