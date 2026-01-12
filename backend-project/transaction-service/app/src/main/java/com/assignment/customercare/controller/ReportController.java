package com.assignment.customercare.controller;

import java.time.Instant;

import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.core.io.buffer.DataBufferFactory;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.assignment.customercare.dto.ReportType;
import com.assignment.customercare.service.ReportService;

import io.swagger.v3.oas.annotations.tags.Tag;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/reports")
@Tag(
    name = "Reports",
    description = "Downloadable CSV reports for transactions"
)
public class ReportController {
    
    private final ReportService reportService;

    public ReportController(ReportService reportService) {
        this.reportService = reportService;
    }

    @GetMapping(value = "/download", produces = "text/csv")
    @PreAuthorize("hasAnyRole('ADMIN','SUPERVISOR')")
    public Mono<ResponseEntity<Flux<DataBuffer>>> downloadTransactionReport(
        @RequestParam ReportType reportType,
        @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) Instant start,
        @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) Instant end,
        ServerHttpResponse response
    ) {
        DataBufferFactory dataBufferFactory = response.bufferFactory();

        Flux<DataBuffer> csvStream = reportService.generateCsv(reportType, start, end)
            .map(line -> dataBufferFactory.wrap((line + "\n").getBytes()));

        return Mono.just(
            ResponseEntity.ok()
            .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=transactions-report.csv")
            .contentType(MediaType.parseMediaType("text/csv"))
            .body(csvStream)
        );
    }
}
