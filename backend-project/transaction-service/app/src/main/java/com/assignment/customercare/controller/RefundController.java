package com.assignment.customercare.controller;

import org.springframework.http.MediaType;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.assignment.customercare.dto.RefundRequest;
import com.assignment.customercare.model.Transaction;
import com.assignment.customercare.service.RefundService;


import jakarta.validation.Valid;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/transactions")
public class RefundController {
    
    private final RefundService refundService;

    public RefundController(RefundService refundService) {
        this.refundService = refundService;
    }

    @PostMapping(value = "/{transactionId}/refund", consumes = MediaType.APPLICATION_JSON_VALUE)
    @PreAuthorize("hasAnyRole('ADMIN','SUPERVISOR')")
    public Mono<Transaction> refund(
        @PathVariable String transactionId, 
        @RequestBody @Valid Mono<RefundRequest> request
    ) {
        return request.flatMap(req -> {
            System.out.println("Trying to refund " + transactionId + " with Rs." + req.amount());
            return refundService.refund(transactionId, req.amount());
        });        
    }
}
