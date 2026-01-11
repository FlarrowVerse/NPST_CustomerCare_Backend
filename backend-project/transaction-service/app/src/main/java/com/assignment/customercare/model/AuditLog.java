package com.assignment.customercare.model;

import java.time.Instant;

import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

@Table("audit_logs")
public class AuditLog {
    
    @Id
    private Long id;

    private String username;
    private String role;
    private String action;
    private String endpoint;
    private Instant timestamp;

    // getters and setters
    public String getAction() {
        return action;
    }
    public String getEndpoint() {
        return endpoint;
    }
    public Long getId() {
        return id;
    }
    public String getRole() {
        return role;
    }
    public String getUsername() {
        return username;
    }
    public Instant getTimestamp() {
        return timestamp;
    }

    public void setAction(String action) {
        this.action = action;
    }
    public void setEndpoint(String endpoint) {
        this.endpoint = endpoint;
    }
    public void setUsername(String username) {
        this.username = username;
    }
    public void setRole(String role) {
        this.role = role;
    }
}
