package com.assignment.gateway.filter;

import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.ReactiveSecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

@Component
public class UserHeaderGlobalFilter implements GlobalFilter {

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {

        return ReactiveSecurityContextHolder.getContext()
                .map(ctx -> ctx.getAuthentication())
                .filter(Authentication::isAuthenticated)
                .map(auth -> exchange.mutate()
                        .request(r -> {
                            r.header("X-User", auth.getName());

                            // send first role (sufficient for audit)
                            GrantedAuthority role =
                                    auth.getAuthorities().iterator().next();
                            r.header("X-Role", role.getAuthority());
                        })
                        .build()
                )
                .defaultIfEmpty(exchange)
                .flatMap(chain::filter);
    }
}
