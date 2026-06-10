package com.projects.fin_analyzer.controller;

import com.projects.fin_analyzer.dto.TransactionRequest;
import com.projects.fin_analyzer.dto.TransactionResponse;
import com.projects.fin_analyzer.entity.User;
import com.projects.fin_analyzer.services.CurrentUserService;
import com.projects.fin_analyzer.services.TransactionService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/transactions")
public class TransactionController {

    private final TransactionService transactionService;
    private final CurrentUserService currentUserService;

    public TransactionController(TransactionService transactionService, CurrentUserService currentUserService) {
        this.transactionService = transactionService;
        this.currentUserService = currentUserService;
    }

    @PostMapping("/saveTransaction")
    public TransactionResponse saveTransaction(@Valid @RequestBody TransactionRequest transactionRequest){
        return transactionService.saveTransaction(transactionRequest);
    }

    @GetMapping("/list")
    public List<TransactionResponse> getTransactionList(){
        User user = currentUserService.getCurrentUser();
        return transactionService.getTransactionList(user.getId());
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
