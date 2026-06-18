package com.projects.fin_analyzer.services;

import com.projects.fin_analyzer.dto.TransactionRequest;
import com.projects.fin_analyzer.dto.TransactionResponse;
import com.projects.fin_analyzer.entity.Transaction;
import com.projects.fin_analyzer.entity.User;
import com.projects.fin_analyzer.mapper.TransactionMapper;
import com.projects.fin_analyzer.repository.*;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Service
public class TransactionService {
    private final TransactionRepository transactionRepository;
    private final CurrentUserService currentUserService;
    private final TransactionMapper transactionMapper;
    public TransactionService(TransactionRepository transactionRepository, UserRepository userRepository, CurrentUserService currentUserService, TransactionMapper transactionMapper) {
        this.transactionRepository = transactionRepository;
        this.currentUserService = currentUserService;
        this.transactionMapper = transactionMapper;
    }

    public TransactionResponse saveTransaction(TransactionRequest transactionRequest){

        Transaction transaction = transactionMapper.toTransactionEntity(transactionRequest);
        User user = currentUserService.getCurrentUser();
        transaction.setUser(user);
        transaction.setTransactionDate(LocalDate.now());
        Transaction savedTransaction = transactionRepository.save(transaction);
        return transactionMapper.toTransactionResponse(savedTransaction);
    }

    public List<TransactionResponse> getTransactionList(){
        User user = currentUserService.getCurrentUser();
        List<Transaction> transactionList = transactionRepository.findByUserId(user.getId());
        return transactionList.stream()
                .map(transactionMapper::toTransactionResponse)
                .toList();
    }

    public TransactionResponse updateTransaction(Long transactionId, TransactionRequest transactionRequest){
        Transaction transaction = transactionRepository.findById(transactionId)
                .orElseThrow(() -> new RuntimeException("Transaction does not exist"));
        transaction.setAmount(transactionRequest.getAmount());
        transaction.setType(transactionRequest.getTransactionType());
        transaction.setCategory(transactionRequest.getCategory());
        transaction.setDescription(transactionRequest.getDescription());
        transaction.setId(transactionId);

        Transaction updatedTransaction = transactionRepository.save(transaction);

        return transactionMapper.toTransactionResponse(updatedTransaction);
    }

    public void deleteTransaction(Long transactionId){
        Transaction transaction = transactionRepository.findById(transactionId)
                .orElseThrow(() -> new RuntimeException("Transaction not found"));
        transactionRepository.delete(transaction);
    }
}
