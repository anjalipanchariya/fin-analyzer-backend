package com.projects.fin_analyzer.services;

import com.projects.fin_analyzer.dto.MonthlyTrendResponse;
import com.projects.fin_analyzer.entity.Transaction;
import com.projects.fin_analyzer.enums.TransactionType;
import com.projects.fin_analyzer.repository.TransactionRepository;
import com.projects.fin_analyzer.repository.UserRepository;
import org.antlr.v4.runtime.tree.Tree;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.YearMonth;
import java.util.*;

@Service
public class MonthlyTrendServiceImpl implements MonthlyTrendService{

    private final TransactionRepository transactionRepository;
    private final UserRepository userRepository;

    public MonthlyTrendServiceImpl(TransactionRepository transactionRepository, UserRepository userRepository) {
        this.transactionRepository = transactionRepository;
        this.userRepository = userRepository;
    }

    @Override
    public List<MonthlyTrendResponse> getMonthlyTrend(Long userId) {
        userRepository.findById(userId).
                orElseThrow(()->new RuntimeException("User not found"));

        List<Transaction> transactionList = transactionRepository.findByUserId(userId);
        Map<YearMonth, BigDecimal> monthlyMap = new TreeMap<>();

        for(Transaction transaction : transactionList){
            if(transaction.getType() == TransactionType.EXPENSE){
                YearMonth month = YearMonth.from(transaction.getTransactionDate());
                monthlyMap.put(month,monthlyMap.getOrDefault(month,BigDecimal.ZERO).add(transaction.getAmount()));
            }
        }

        List<MonthlyTrendResponse> response = new ArrayList<>();
        for(Map.Entry<YearMonth, BigDecimal> entry : monthlyMap.entrySet()){
            MonthlyTrendResponse monthlyTrendResponse = new MonthlyTrendResponse();
            monthlyTrendResponse.setMonth(entry.getKey().toString());
            monthlyTrendResponse.setExpense(entry.getValue());
            response.add(monthlyTrendResponse);
        }
        return response;
    }
}
