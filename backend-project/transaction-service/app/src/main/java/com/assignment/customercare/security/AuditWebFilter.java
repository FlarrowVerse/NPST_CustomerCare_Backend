package com.assignment.customercare.security;

import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.web.server.WebFilter;
import org.springframework.web.server.WebFilterChain;

import com.assignment.customercare.service.AuditService;

import reactor.core.publisher.Mono;

@Component
public class AuditWebFilter implements WebFilter {

    private final AuditService auditService;

    public AuditWebFilter(AuditService auditService) {
        this.auditService = auditService;
    }

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, WebFilterChain chain) {

        String path = exchange.getRequest().getPath().value();
        String method = exchange.getRequest().getMethod().name();

        // Identity propagated from gateway
        String username = exchange.getRequest()
                                  .getHeaders()
                                  .getFirst("X-User");

        String role = exchange.getRequest()
                              .getHeaders()
                              .getFirst("X-Role");

        if (username == null) {
            username = "UNKNOWN";
        }
        if (role == null) {
            role = "UNKNOWN";
        }

        String finalUsername = username;
        String finalRole = role;

        return chain.filter(exchange)
                .then(
                        auditService.log(
                                finalUsername,
                                finalRole,
                                method,
                                path
                        )
                );
    }
}
