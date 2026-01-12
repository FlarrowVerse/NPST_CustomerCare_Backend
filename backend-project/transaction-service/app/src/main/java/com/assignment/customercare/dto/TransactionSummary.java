package com.assignment.customercare.dto;

public record TransactionSummary(
    Long total,
    Long success,
    Long failed,
    Long pending,
    Long refunds
) {}
