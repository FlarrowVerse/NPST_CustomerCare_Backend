package com.assignment.customercare.repository;

import org.springframework.data.repository.reactive.ReactiveCrudRepository;

import com.assignment.customercare.model.AuditLog;

import reactor.core.publisher.Flux;

public interface AuditLogRepository extends ReactiveCrudRepository<AuditLog, Long> {

    Flux<AuditLog> findByUsername(String username);
    
}
