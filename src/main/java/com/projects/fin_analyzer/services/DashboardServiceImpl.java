package com.projects.fin_analyzer.services;

import com.projects.fin_analyzer.dto.DashboardResponse;
import com.projects.fin_analyzer.entity.Transaction;
import com.projects.fin_analyzer.entity.User;
import com.projects.fin_analyzer.enums.Category;
import com.projects.fin_analyzer.enums.TransactionType;
import com.projects.fin_analyzer.repository.TransactionRepository;
import com.projects.fin_analyzer.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class DashboardServiceImpl implements DashboardService{

    private final TransactionRepository transactionRepository;
    private final UserRepository userRepository;
    private final CurrentUserService currentUserService;
    public DashboardServiceImpl(TransactionRepository transactionRepository, UserRepository userRepository, CurrentUserService currentUserService) {
        this.transactionRepository = transactionRepository;
        this.userRepository = userRepository;
        this.currentUserService = currentUserService;
    }

    @Override
    public DashboardResponse getDashboard() {
        User user = currentUserService.getCurrentUser();
        Long userId = user.getId();

        DashboardResponse dashboardResponse =
                new DashboardResponse();

        userRepository.findById(userId)
                .orElseThrow(
                        () -> new RuntimeException("User not found")
                );
        List<Transaction> transactionList = transactionRepository.findByUserId(userId);

        BigDecimal totalIncome = transactionList.stream()
                .filter((t) -> t.getType() == TransactionType.INCOME)
                .map(Transaction::getAmount)
                .reduce(BigDecimal.ZERO,BigDecimal::add);

        BigDecimal totalExpense = transactionList.stream()
                .filter((t) -> t.getType() == TransactionType.EXPENSE)
                .map(transaction -> transaction.getAmount())
                .reduce(BigDecimal.ZERO,BigDecimal::add);

        BigDecimal savings = totalIncome.subtract(totalExpense);

        BigDecimal savingsRate = BigDecimal.ZERO;

        if(totalIncome.compareTo(BigDecimal.ZERO)>0){
            savingsRate = savings.multiply(BigDecimal.valueOf(100))
                    .divide(totalIncome,2, RoundingMode.HALF_UP);
        }

        Long transactionCount = (long) transactionList.size();

        Map<Category, BigDecimal> categoryMap = new HashMap<>();

        for(Transaction transaction : transactionList){
            if(transaction.getType()==TransactionType.EXPENSE){
                Category category = transaction.getCategory();
                categoryMap.put(category,categoryMap.getOrDefault(category,BigDecimal.ZERO).add(transaction.getAmount()));
            }
        }
        Category topCategory = null;
        BigDecimal maxAmount = BigDecimal.ZERO;
        for(Map.Entry<Category,BigDecimal> entry : categoryMap.entrySet()){
            if(entry.getValue().compareTo(maxAmount) > 0){
                maxAmount = entry.getValue();
                topCategory = entry.getKey();
            }
        }
        dashboardResponse.setTotalIncome(totalIncome);
        dashboardResponse.setTotalExpense(totalExpense);
        dashboardResponse.setSavings(savings);
        dashboardResponse.setSavingsRate(savingsRate);
        dashboardResponse.setTransactionCount(transactionCount);
        dashboardResponse.setTopExpenseCategory(topCategory);

        return dashboardResponse;
    }
}
