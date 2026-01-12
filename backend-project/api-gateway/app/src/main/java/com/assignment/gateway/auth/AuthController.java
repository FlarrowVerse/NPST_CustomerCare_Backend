package com.assignment.gateway.auth;

import com.assignment.gateway.security.UserStore;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;

import javax.crypto.SecretKey;
import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/auth")
public class AuthController {

    private final UserStore userStore;

    @Value("${spring.security.oauth2.resourceserver.jwt.secret-key}")
    private String secret;

    private SecretKey key;

    public AuthController(UserStore userStore) {
        this.userStore = userStore;
    }

    @PostConstruct
    void init() {
        this.key = Keys.hmacShaKeyFor(secret.getBytes());
    }

    @PostMapping("/token")
    public String token(
            @RequestParam String username,
            @RequestParam String password
    ) {
        var user = userStore.getUser(username);

        if (user == null || !userStore.matches(password, user.passwordHash())) {
            throw new RuntimeException("Invalid credentials");
        }

        return Jwts.builder()
                .setSubject(username)
                .claim("roles", List.of(user.role()))
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + 3600_000))
                .signWith(key, SignatureAlgorithm.HS256)
                .compact();
    }
}
