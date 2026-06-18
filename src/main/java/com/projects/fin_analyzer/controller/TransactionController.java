package com.projects.fin_analyzer.controller;

import com.projects.fin_analyzer.dto.TransactionRequest;
import com.projects.fin_analyzer.dto.TransactionResponse;
import com.projects.fin_analyzer.services.TransactionService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/transactions")
public class TransactionController {

    private final TransactionService transactionService;

    public TransactionController(TransactionService transactionService) {
        this.transactionService = transactionService;
    }

    @PostMapping("/saveTransaction")
    public TransactionResponse saveTransaction(@Valid @RequestBody TransactionRequest transactionRequest){
        return transactionService.saveTransaction(transactionRequest);
    }

    @GetMapping("/list")
    public List<TransactionResponse> getTransactionList(){
        return transactionService.getTransactionList();
    }

    @PutMapping("/{transactionId}")
    public TransactionResponse updateTransaction(@PathVariable Long transactionId, @RequestBody TransactionRequest transactionRequest){
        return transactionService.updateTransaction(transactionId,transactionRequest);
    }

    @DeleteMapping("/{transactionId}")
    public ResponseEntity<Void> deleteTransaction(@PathVariable Long transactionId){
        transactionService.deleteTransaction(transactionId);
        return ResponseEntity.noContent().build();
    }
}
