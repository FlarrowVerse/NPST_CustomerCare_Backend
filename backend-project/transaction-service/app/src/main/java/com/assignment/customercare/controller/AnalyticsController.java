package com.assignment.customercare.controller;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.assignment.customercare.dto.DailyTrend;
import com.assignment.customercare.dto.RefundAnalytics;
import com.assignment.customercare.dto.TransactionSummary;
import com.assignment.customercare.service.AnalyticsService;

import io.swagger.v3.oas.annotations.tags.Tag;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/analytics")
@Tag(
    name = "Analytics Endpoints",
    description = "Transaction analytics APIs (summary, daily trends, refund statistics)"
)
public class AnalyticsController {
    
    private final AnalyticsService analyticsService;

    public AnalyticsController(AnalyticsService analyticsService) {
        this.analyticsService = analyticsService;
    }

    @GetMapping("/summary")
    @PreAuthorize("hasAnyRole('ADMIN','SUPERVISOR')")
    public Mono<TransactionSummary> summary() {
        System.out.println("Coming into summary");
        return analyticsService.summary();
    }

    @GetMapping("/daily")
    @PreAuthorize("hasAnyRole('ADMIN','SUPERVISOR')")
    public Flux<DailyTrend> daily(@RequestParam int days) {
        System.out.println("Coming into daily " + days);
        return analyticsService.dailyTrends(days);
    }

    @GetMapping("/refunds")
    @PreAuthorize("hasAnyRole('ADMIN','SUPERVISOR')")
    public Mono<RefundAnalytics> refunds() {
        System.out.println("Coming into refunds");
        return analyticsService.refundAnalytics();
    }
}
