package com.assignment.customercare.service;

import java.time.Instant;
import java.time.temporal.ChronoUnit;

import org.springframework.stereotype.Service;

import com.assignment.customercare.dto.DailyTrend;
import com.assignment.customercare.dto.RefundAnalytics;
import com.assignment.customercare.dto.TransactionSummary;
import com.assignment.customercare.repository.TransactionRepository;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class AnalyticsService {

    private final TransactionRepository repository;

    public AnalyticsService(TransactionRepository repository) {
        this.repository = repository;
    }

    public Mono<TransactionSummary> summary() {
        return repository.summary();
    }
    
                

    public Flux<DailyTrend> dailyTrends(int days) {
        Instant start = Instant.now().minus(days, ChronoUnit.DAYS);
        
        return repository.dailyTrends(start);
    }

    public Mono<RefundAnalytics> refundAnalytics() {
        return repository.refundStats()
            .map(p -> {
                double percentage = p.total() == 0 ? 0.0 : (p.refunds() * 100.0) / p.total();
                return new RefundAnalytics(p.total(), p.refunds(), percentage);
            });
    }
    
}