package com.pragma.powerup.usermicroservice.infrastructure.security;

import com.pragma.powerup.usermicroservice.domain.model.User;
import com.pragma.powerup.usermicroservice.domain.spi.ITokenProviderPort;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import javax.crypto.SecretKey;

public class JwtProviderAdapter implements ITokenProviderPort {

    // In a real scenario, this must be in environment variables.
    // 256-bit (32 byte) secret key
    private static final String SECRET = "mySuperSecureSecretKeyForPragmaPowerUpProject2025"; 
    private static final long EXPIRATION_TIME = 3600000; // 1 hour in milliseconds

    @Override
    public String generateToken(User user) {
        SecretKey key = Keys.hmacShaKeyFor(SECRET.getBytes());
        
        List<String> roles = Collections.singletonList(user.getRole());

        return Jwts.builder()
                .subject(user.getEmail())
                .claim("roles", roles)
                .claim("id", user.getId())
                .issuedAt(new Date())
                .expiration(new Date(System.currentTimeMillis() + EXPIRATION_TIME))
                .signWith(key)
                .compact();
    }

    public Claims getClaims(String token) {
        SecretKey key = Keys.hmacShaKeyFor(SECRET.getBytes());
        return Jwts.parser()
                .verifyWith(key)
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }

    public String getUsername(String token) {
        return getClaims(token).getSubject();
    }

    public List<String> getRoles(String token) {
        return getClaims(token).get("roles", List.class);
    }

    public boolean validateToken(String token) {
        try {
            getClaims(token);
            return true;
        } catch (JwtException | IllegalArgumentException e) {
            return false;
        }
    }
}
