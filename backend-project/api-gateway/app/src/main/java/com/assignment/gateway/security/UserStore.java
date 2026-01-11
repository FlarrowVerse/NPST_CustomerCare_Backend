package com.assignment.gateway.security;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
public class UserStore {

    private final BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();

    private final Map<String, UserRecord> users = Map.of(
            "agent", new UserRecord("agent", encoder.encode("agent123"), "AGENT"),
            "supervisor", new UserRecord("supervisor", encoder.encode("super123"), "SUPERVISOR"),
            "admin", new UserRecord("admin", encoder.encode("admin123"), "ADMIN")
    );

    public UserRecord getUser(String username) {
        return users.get(username);
    }

    public boolean matches(String raw, String encoded) {
        return encoder.matches(raw, encoded);
    }

    public record UserRecord(String username, String passwordHash, String role) {}
}
