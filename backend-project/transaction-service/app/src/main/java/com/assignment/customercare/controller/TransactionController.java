package com.assignment.customercare.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.assignment.customercare.model.Transaction;
import com.assignment.customercare.service.TransactionService;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
public class TransactionController {

    private final TransactionService service;

    public TransactionController(TransactionService service) {
        this.service = service;
    }
    
    @GetMapping("/health")
    public Mono<String> health() {
        return Mono.just("Transaction Service is UP");
    }

    @GetMapping("/transactions")
    public Flux<Transaction> getTransactions(
        @RequestParam(required = false) String status
    ) {
        if (status != null) {
            return service.getTransactionByStatus(status);
        }
        
        return service.getAllTransactions();
    }
}
