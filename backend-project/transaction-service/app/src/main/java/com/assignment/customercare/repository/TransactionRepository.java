package com.assignment.customercare.repository;

import org.springframework.data.repository.reactive.ReactiveCrudRepository;

import com.assignment.customercare.model.Transaction;

import reactor.core.publisher.Flux;

public interface TransactionRepository extends ReactiveCrudRepository<Transaction, Long> {
    
    Flux<Transaction> findByStatus(String status);
}
