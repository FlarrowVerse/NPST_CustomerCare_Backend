package com.assignment.customercare.service;

import org.springframework.stereotype.Service;

import com.assignment.customercare.model.AuditLog;
import com.assignment.customercare.repository.AuditLogRepository;

import reactor.core.publisher.Mono;

@Service
public class AuditService {
    
    private final AuditLogRepository repository;

    public AuditService(AuditLogRepository repository) {
        this.repository = repository;
    }

    public Mono<Void> log(String username, String role, String action, String endpoint) {
        AuditLog log = new AuditLog();
        log.setUsername(username);
        log.setRole(role);
        log.setAction(action);
        log.setEndpoint(endpoint);

        return repository.save(log).then();
    }
}
