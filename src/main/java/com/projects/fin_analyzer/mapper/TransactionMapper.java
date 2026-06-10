package com.projects.fin_analyzer.mapper;

import com.projects.fin_analyzer.dto.TransactionRequest;
import com.projects.fin_analyzer.dto.TransactionResponse;
import com.projects.fin_analyzer.entity.Transaction;
import org.springframework.stereotype.Component;

@Component
public class TransactionMapper {

    public Transaction toTransactionEntity(TransactionRequest transactionRequest){
        Transaction transaction = new Transaction();
        transaction.setAmount(transactionRequest.getAmount());
        transaction.setType(transactionRequest.getTransactionType());
        transaction.setCategory(transactionRequest.getCategory());
        transaction.setDescription(transactionRequest.getDescription());
        return transaction;
    }

    public TransactionResponse toTransactionResponse(Transaction transaction){
        TransactionResponse transactionResponse = new TransactionResponse();
        transactionResponse.setAmount(transaction.getAmount());
        transactionResponse.setCategory(transaction.getCategory());
        transactionResponse.setTransactionType(transaction.getType());
        transactionResponse.setDescription(transaction.getDescription());
        transactionResponse.setUserId(transaction.getUser().getId());
        transactionResponse.setTransactionId(transaction.getId());
        transactionResponse.setTransactionDate(transaction.getTransactionDate());
        return transactionResponse;
    }

}
