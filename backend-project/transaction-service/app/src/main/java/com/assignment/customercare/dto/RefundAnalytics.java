package com.assignment.customercare.dto;

public record RefundAnalytics(
    Long total,
    Long refunds,
    Double percentage
) {}
