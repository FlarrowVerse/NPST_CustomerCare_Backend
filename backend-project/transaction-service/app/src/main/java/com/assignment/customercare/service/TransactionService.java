package com.assignment.customercare.service;

// import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;

import com.assignment.customercare.model.Transaction;
import com.assignment.customercare.repository.TransactionRepository;

import reactor.core.publisher.Flux;

@Service
public class TransactionService {
    
    private final TransactionRepository repository;

    public TransactionService(TransactionRepository repository) {
        this.repository = repository;
    }

    // @PreAuthorize("hasRole('AGENT') or hasRole('SUPERVISOR') or hasRole('ADMIN')")
    public Flux<Transaction> getAllTransactions() {
        return repository.findAll();
    }

    // @PreAuthorize("hasRole('AGENT') or hasRole('SUPERVISOR') or hasRole('ADMIN')")
    public Flux<Transaction> getTransactionByStatus(String status) {
        return repository.findByStatus(status);
    }
}
