package com.assignment.customercare.service;

import java.time.Instant;

import org.springframework.stereotype.Service;

import com.assignment.customercare.dto.ReportType;
import com.assignment.customercare.model.Transaction;
import com.assignment.customercare.repository.TransactionRepository;

import reactor.core.publisher.Flux;

@Service
public class ReportService {
    
    private final TransactionRepository repository;

    public ReportService(TransactionRepository repository) {
        this.repository = repository;
    }

    public Flux<String> generateCsv(ReportType reportType, Instant start, Instant end) {
        Flux<Transaction> transactions = repository.findByCreatedAtBetween(start, end);

        return Flux.concat(
            Flux.just(csvHeader()),
            transactions.map(this::toCsvRow)
        );
    }

    private String csvHeader() {
        return "TransactionId,Amount,Status,CustomerId,Merchant,PaymentMethod,CreatedAt";
    }

    private String toCsvRow(Transaction tx) {
        return String.join(",",
            tx.getTransactionId(),
            tx.getAmount().toString(),
            tx.getStatus(),
            tx.getCustomerId(),
            tx.getMerchant(),
            tx.getPaymentMethod(),
            tx.getCreatedAt().toString()
        );
    }

}
