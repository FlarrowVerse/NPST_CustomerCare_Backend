package com.assignment.customercare.dto;

import java.math.BigDecimal;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

public record RefundRequest(
    @NotNull
    @Positive
    BigDecimal amount
) {}
