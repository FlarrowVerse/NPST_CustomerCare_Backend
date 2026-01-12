package com.assignment.customercare.controller;

import java.time.Duration;
import java.time.Instant;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.format.annotation.DateTimeFormat.ISO;
import org.springframework.http.MediaType;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.assignment.customercare.model.Transaction;
import com.assignment.customercare.service.TransactionService;

import io.swagger.v3.oas.annotations.tags.Tag;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/transactions")
@Tag(
    name = "Transactions",
    description = "Transaction creation, retrieval, filtering, refunds, and live feed APIs"
)
public class TransactionController {

    private final TransactionService service;

    public TransactionController(TransactionService service) {
        this.service = service;
    }
    
    @GetMapping("/health")
    public Mono<String> health() {
        return Mono.just("Transaction Service is UP");
    }

    @GetMapping
    @PreAuthorize("hasAnyRole('ADMIN','SUPERVISOR','AGENT')")
    public Flux<Transaction> getTransactions(
        @RequestParam(required = false) String status,
        @RequestParam(required = false) String merchant,
        @RequestParam(required = false) String paymentMethod,
        @RequestParam(required = false) @DateTimeFormat(iso = ISO.DATE_TIME) Instant start,
        @RequestParam(required = false) @DateTimeFormat(iso = ISO.DATE_TIME) Instant end
    ) {
        if (status != null) {
            return service.getTransactionByStatus(status);
        } else {
            return service.filter(merchant, paymentMethod, start, end);
        }
    }

    /**
     * All Filters
     */
    // By Id
    @GetMapping("/{transactionId}")
    @PreAuthorize("hasAnyRole('ADMIN','SUPERVISOR','AGENT')")
    public Mono<Transaction> getTransactionById(@PathVariable String transactionId) {
        return service.findTransactionById(transactionId);        
    }

    // by Customer Id
    @GetMapping("/customer/{customerId}")
    @PreAuthorize("hasAnyRole('ADMIN','SUPERVISOR')")
    public Flux<Transaction> getTransactionByCustomerId(@PathVariable String customerId) {
        return service.findTransactionByCustomerId(customerId);
    }

    // real-time feed sorted by latest timestamp
    @GetMapping(value = "/feed/stream", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    @PreAuthorize("hasAnyRole('ADMIN','SUPERVISOR')")
    public Flux<Transaction> liveStream() {
        return service.liveFeed().delayElements(Duration.ofSeconds(1));
    }

}
