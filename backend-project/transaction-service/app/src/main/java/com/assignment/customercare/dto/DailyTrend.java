package com.assignment.customercare.dto;

import java.time.LocalDate;

public record DailyTrend(
    LocalDate startDate,
    Long success,
    Long failed
) {}
