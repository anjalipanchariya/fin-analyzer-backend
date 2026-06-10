package com.projects.fin_analyzer.services;

import com.projects.fin_analyzer.dto.TransactionRequest;
import com.projects.fin_analyzer.dto.TransactionResponse;
import com.projects.fin_analyzer.entity.Transaction;
import com.projects.fin_analyzer.entity.User;
import com.projects.fin_analyzer.mapper.TransactionMapper;
import com.projects.fin_analyzer.repository.*;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Service
public class TransactionService {
    private final TransactionRepository transactionRepository;
    private final UserRepository userRepository;
    private final TransactionMapper transactionMapper;
    public TransactionService(TransactionRepository transactionRepository, UserRepository userRepository, TransactionMapper transactionMapper) {
        this.transactionRepository = transactionRepository;
        this.userRepository = userRepository;
        this.transactionMapper = transactionMapper;
    }

    public TransactionResponse saveTransaction(TransactionRequest transactionRequest){
        if(userRepository.findById(transactionRequest.getUserId()).isEmpty()){
            throw new RuntimeException("The user not present");
        }
        Transaction transaction = transactionMapper.toTransactionEntity(transactionRequest);
        Long userId = transactionRequest.getUserId();
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));
        transaction.setUser(user);
        transaction.setTransactionDate(LocalDate.now());
        Transaction savedTransaction = transactionRepository.save(transaction);
        return transactionMapper.toTransactionResponse(savedTransaction);
    }

    public List<TransactionResponse> getTransactionList(Long userId){
        List<Transaction> transactionList = transactionRepository.findByUserId(userId);
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
