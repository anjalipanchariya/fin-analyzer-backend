package com.projects.fin_analyzer.services;

import com.projects.fin_analyzer.dto.DashboardResponse;
import com.projects.fin_analyzer.entity.Transaction;
import com.projects.fin_analyzer.entity.User;
import com.projects.fin_analyzer.enums.Category;
import com.projects.fin_analyzer.enums.TransactionType;
import com.projects.fin_analyzer.repository.TransactionRepository;
import com.projects.fin_analyzer.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@ExtendWith(MockitoExtension.class)
public class DashboardServiceImplTest {

    @Mock
    private TransactionRepository transactionRepository;

    @Mock
    private UserRepository userRepository;

    @Mock
    private CurrentUserService currentUserService;

    @InjectMocks
    private DashboardServiceImpl dashboardService;

    @Test
    void shouldCalculateSavings(){

        User user = new User();
        user.setId(1L);
        user.setEmail("test@test.com");
        when(currentUserService.getCurrentUser())
                .thenReturn(user);

        when(userRepository.findById(1L))
                .thenReturn(Optional.of(user));

        Transaction income = new Transaction();
        income.setType(TransactionType.INCOME);
        income.setAmount(BigDecimal.valueOf(50000));

        Transaction expense = new Transaction();
        expense.setType(TransactionType.EXPENSE);
        expense.setAmount(BigDecimal.valueOf(20000));

        when(transactionRepository.findByUserId(1L))
                .thenReturn(List.of(income, expense));

        DashboardResponse response =
                dashboardService.getDashboard();

        assertEquals(
                BigDecimal.valueOf(30000),
                response.getSavings()
        );
    }

    @Test
    void shouldCalculateSavingsRate(){
        User user = new User();
        user.setId(1L);
        when(currentUserService.getCurrentUser())
                .thenReturn(user);

        when(userRepository.findById(1L))
                .thenReturn(Optional.of(user));

        Transaction income = new Transaction();
        income.setType(TransactionType.INCOME);
        income.setAmount(BigDecimal.valueOf(100000));

        Transaction expense = new Transaction();
        expense.setType(TransactionType.EXPENSE);
        expense.setAmount(BigDecimal.valueOf(40000));

        when(transactionRepository.findByUserId(1L))
                .thenReturn(List.of(income, expense));

        DashboardResponse response =
                dashboardService.getDashboard();

        assertEquals(
                BigDecimal.valueOf(60.00).setScale(2),
                response.getSavingsRate()
        );
    }

    @Test
    void shouldCalculateTopExpenseCategory(){
        User user = new User();
        user.setId(1L);
        when(currentUserService.getCurrentUser())
                .thenReturn(user);

        when(userRepository.findById(1L))
                .thenReturn(Optional.of(user));


        Transaction food = new Transaction();
        food.setType(TransactionType.EXPENSE);
        food.setAmount(BigDecimal.valueOf(200));
        food.setCategory(Category.FOOD);

        Transaction entertainment = new Transaction();
        entertainment.setType(TransactionType.EXPENSE);
        entertainment.setAmount(BigDecimal.valueOf(2000));
        entertainment.setCategory(Category.ENTERTAINMENT);

        Transaction petrol = new Transaction();
        petrol.setType(TransactionType.EXPENSE);
        petrol.setAmount(BigDecimal.valueOf(387));
        petrol.setCategory(Category.PETROL);

        when(transactionRepository.findByUserId(1L))
                .thenReturn(List.of(food, entertainment, petrol));

        DashboardResponse response =
                dashboardService.getDashboard();

        assertEquals(
                Category.ENTERTAINMENT,
                response.getTopExpenseCategory()
        );
    }

    @Test
    void shouldReturnZeroSavingsRateWhenIncomeIsZero(){
        User user = new User();
        user.setId(1L);
        when(currentUserService.getCurrentUser())
                .thenReturn(user);

        when(userRepository.findById(1L))
                .thenReturn(Optional.of(user));

        Transaction income = new Transaction();
        income.setType(TransactionType.INCOME);
        income.setAmount(BigDecimal.ZERO);

        when(transactionRepository.findByUserId(1L))
                .thenReturn(List.of(income));
        DashboardResponse response =
                dashboardService.getDashboard();
        assertEquals(
                BigDecimal.ZERO,
                response.getSavingsRate()
        );
    }
}
