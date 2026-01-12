package com.assignment.customercare.repository;

import java.time.Instant;

import org.springframework.data.r2dbc.repository.Query;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;

import com.assignment.customercare.dto.DailyTrend;
import com.assignment.customercare.dto.RefundValues;
import com.assignment.customercare.dto.TransactionSummary;
import com.assignment.customercare.model.Transaction;


import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface TransactionRepository extends ReactiveCrudRepository<Transaction, Long> {
    
    /**
     * All types of Filters
     */
    // By Id
    Mono<Transaction> findByTransactionId(String transactionId);

    // By parent transaction Ids
    Flux<Transaction> findByParentTransactionId(String parentTransactionId);

    // By Status
    Flux<Transaction> findByStatus(String status);

    // by Customer Id
    Flux<Transaction> findByCustomerId(String customerId);

    // by Created At duration
    Flux<Transaction> findByCreatedAtBetween(Instant start, Instant end);

    // by Merchant
    Flux<Transaction> findByMerchant(String merchant);

    // by payment method
    Flux<Transaction> findByPaymentMethod(String paymentMethod);

    // combo filter
    @Query("""
        SELECT * FROM transactions
        WHERE (:merchant IS NULL OR merchant = :merchant) 
        AND (:paymentMethod IS NULL OR payment_method = :paymentMethod) 
        AND (:start IS NULL OR created_at >= :start) 
        AND (:end IS NULL OR created_at <= :end) 
        ORDER BY created_at DESC
    """)
    Flux<Transaction> filterTransactions(String merchant, String paymentMethod, Instant start, Instant end);

    // real-time feed sorted by latest timestamp
    Flux<Transaction> findTop10ByOrderByCreatedAtDesc();

    /**
     * Support for Analytics module
     */
    @Query("""
        SELECT
            COUNT(*)::BIGINT AS total,
            SUM(CASE WHEN status = 'SUCCESS' THEN 1 ELSE 0 END)::BIGINT AS success,
            SUM(CASE WHEN status = 'FAILED' THEN 1 ELSE 0 END)::BIGINT AS failed,
            SUM(CASE WHEN status = 'PENDING' THEN 1 ELSE 0 END)::BIGINT AS pending,
            SUM(CASE WHEN status = 'REFUND' THEN 1 ELSE 0 END)::BIGINT AS refunds
        FROM transactions
    """)
    Mono<TransactionSummary> summary();


    @Query("""
        SELECT
            created_at::DATE AS start_date,
            SUM(CASE WHEN status = 'SUCCESS' THEN 1 ELSE 0 END)::BIGINT AS success,
            SUM(CASE WHEN status = 'FAILED' THEN 1 ELSE 0 END)::BIGINT AS failed
        FROM transactions
        WHERE created_at >= :start
        GROUP BY 1
        ORDER BY 1
    """)
    Flux<DailyTrend> dailyTrends(Instant start);


    @Query("""
        SELECT
            COUNT(*)::BIGINT AS total,
            SUM(CASE WHEN status = 'REFUND' THEN 1 ELSE 0 END)::BIGINT AS refunds
        FROM transactions
    """)
    Mono<RefundValues> refundStats();   
}