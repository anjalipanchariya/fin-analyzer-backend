package com.projects.fin_analyzer.services;

import com.projects.fin_analyzer.dto.FinancialHealthResponse;
import com.projects.fin_analyzer.entity.Transaction;
import com.projects.fin_analyzer.entity.User;
import com.projects.fin_analyzer.enums.ScoreStatus;
import com.projects.fin_analyzer.enums.TransactionType;
import com.projects.fin_analyzer.repository.TransactionRepository;
import com.projects.fin_analyzer.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.YearMonth;
import java.util.List;

@Service
public class FinancialHealthServiceImpl implements FinancialHealthService{
    private final TransactionRepository transactionRepository;
    private final UserRepository userRepository;
    private final CurrentUserService currentUserService;

    public FinancialHealthServiceImpl(TransactionRepository transactionRepository, UserRepository userRepository, CurrentUserService currentUserService) {
        this.transactionRepository = transactionRepository;
        this.userRepository = userRepository;
        this.currentUserService = currentUserService;
    }

    @Override
    public FinancialHealthResponse getFinancialHealth() {
        User user = currentUserService.getCurrentUser();
        Long userId = user.getId();

        FinancialHealthResponse financialHealthResponse = new FinancialHealthResponse();
        userRepository.findById(userId)
                .orElseThrow(()->new RuntimeException("User not found"));
        List<Transaction> transactionList = transactionRepository.findByUserId(userId);
        YearMonth currMonth = YearMonth.now();
        BigDecimal totalIncome = BigDecimal.ZERO;
        BigDecimal totalExpense = BigDecimal.ZERO;
        for(Transaction transaction : transactionList){
            if(!YearMonth.from(transaction.getTransactionDate()).equals(currMonth)){
                continue;
            }

            if(transaction.getType() == TransactionType.INCOME){
                totalIncome = totalIncome.add(transaction.getAmount());
            }
            else{
                totalExpense = totalExpense.add(transaction.getAmount());
            }
        }
        if(totalIncome.compareTo(BigDecimal.ZERO)==0){
            financialHealthResponse.setStatus(ScoreStatus.NOT_ENOUGH_DATA.toString());
            financialHealthResponse.setScore(0);
            financialHealthResponse.setSavingsRate(BigDecimal.ZERO);
            return financialHealthResponse;
        }

        BigDecimal savings = totalIncome.subtract(totalExpense);
        BigDecimal savingsRate = savings.multiply(BigDecimal.valueOf(100)).divide(totalIncome,2, RoundingMode.HALF_UP);

        if(savingsRate.compareTo(BigDecimal.ZERO)<0){
            financialHealthResponse.setSavingsRate(savingsRate);
            financialHealthResponse.setScore(0);
            financialHealthResponse.setStatus(ScoreStatus.POOR.toString());
        }
        else if(savingsRate.compareTo(BigDecimal.valueOf(40))>=0){
            financialHealthResponse.setSavingsRate(savingsRate);
            financialHealthResponse.setScore(100);
            financialHealthResponse.setStatus(ScoreStatus.EXCELLENT.toString());
        }
        else if(savingsRate.compareTo(BigDecimal.valueOf(20))>=0){
            financialHealthResponse.setSavingsRate(savingsRate);
            financialHealthResponse.setScore(75);
            financialHealthResponse.setStatus(ScoreStatus.GOOD.toString());
        }
        else if(savingsRate.compareTo(BigDecimal.valueOf(10))>=0){
            financialHealthResponse.setSavingsRate(savingsRate);
            financialHealthResponse.setScore(25);
            financialHealthResponse.setStatus(ScoreStatus.BAD.toString());
        }
        else if(savingsRate.compareTo(BigDecimal.valueOf(0))>=0){
            financialHealthResponse.setSavingsRate(savingsRate);
            financialHealthResponse.setScore(10);
            financialHealthResponse.setStatus(ScoreStatus.VERY_BAD.toString());
        }
        return financialHealthResponse;
    }
}
