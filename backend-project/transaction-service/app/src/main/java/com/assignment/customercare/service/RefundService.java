package com.assignment.customercare.service;

import java.math.BigDecimal;
import java.time.Instant;

import org.springframework.stereotype.Service;

import com.assignment.customercare.model.Transaction;
import com.assignment.customercare.repository.TransactionRepository;

import reactor.core.publisher.Mono;

@Service
public class RefundService {
    
    private final TransactionRepository repository;

    public RefundService(TransactionRepository repository) {
        this.repository = repository;
    }

    public Mono<Transaction> refund(String transactionId, BigDecimal amount) {

        return repository.findByTransactionId(transactionId)
            .switchIfEmpty(Mono.error(new IllegalStateException("Transaction not found")))
            .flatMap(original -> {

                if (!"SUCCESS".equals(original.getStatus())) {
                    return Mono.error(new IllegalStateException("Only SUCCESS-ful transactions can be refunded"));
                }

                if (amount.compareTo(original.getAmount()) > 0) {
                    return Mono.error(new IllegalStateException("Refund amount exceeds original amount"));
                }

                if (Boolean.TRUE.equals(original.getRefunded())) {
                    return Mono.error(new IllegalStateException("Transaction was already refunded"));
                }

                // create refund transaction
                Transaction refund = new Transaction(
                    0, 
                    "REF-" + original.getTransactionId(), 
                    amount.negate(), 
                    "REFUND", 
                    original.getCustomerId(), 
                    original.getMerchant(), 
                    original.getPaymentMethod(), 
                    Instant.now(), 
                    true,
                    original.getTransactionId()
                );

                original.setRefunded(true);

                return repository.save(original).then(repository.save(refund));
            });
    }
}
