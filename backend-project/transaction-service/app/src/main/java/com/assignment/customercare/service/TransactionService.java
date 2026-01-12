package com.assignment.customercare.service;

import java.time.Duration;
import java.time.Instant;

// import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;

import com.assignment.customercare.model.Transaction;
import com.assignment.customercare.repository.TransactionRepository;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

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

    /**
     * All Filters
     */
    // By Status
    public Flux<Transaction> getTransactionByStatus(String status) {
        return repository.findByStatus(status);
    }

    // By Id
    public Mono<Transaction> findTransactionById(String transactionId) {
        return repository.findByTransactionId(transactionId);
    }

    // by Customer Id
    public Flux<Transaction> findTransactionByCustomerId(String customerId) {
        return repository.findByCustomerId(customerId);
    }

    // by Created At duration
    public Flux<Transaction> findTransactionByCreatedAtBetween(Instant start, Instant end) {
        return repository.findByCreatedAtBetween(start, end);
    }

    // by Merchant
    public Flux<Transaction> findTransactionByMerchant(String merchant) {
        return repository.findByMerchant(merchant);
    }

    // by payment method
    public Flux<Transaction> findTransactionByPaymentMethod(String paymentMethod) {
        return repository.findByPaymentMethod(paymentMethod);
    }

    // combo filter
    public Flux<Transaction> filter(String merchant, String paymentMethod, Instant start, Instant end) {
        return repository.filterTransactions(merchant, paymentMethod, start, end);
    }

    // real-time feed sorted by latest timestamp
    public Flux<Transaction> liveFeed() {
        return Flux.interval(Duration.ofSeconds(1))
            .flatMap(tick -> repository.findTop10ByOrderByCreatedAtDesc());
    }

}
