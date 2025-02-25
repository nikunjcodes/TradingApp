package com.example.tradingapp.config;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;

import javax.crypto.SecretKey;
import java.util.Collection;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

public class JwtProvider {
    private static final SecretKey key = Keys.hmacShaKeyFor(JwtConstant.SECRETE_KEY.getBytes());

    public static String generateToken(Authentication auth) {
        // Extract authorities
        Collection<? extends GrantedAuthority> authorities = auth.getAuthorities();
        String roles = populateAuthorities(authorities);

        // Build and sign the JWT
        return Jwts.builder()
                .setIssuedAt(new Date())
                .setExpiration(new Date(new Date().getTime() + 86400000)) // Token expiration
                .claim("email", auth.getName())
                .claim("authorities", roles)
                .signWith(key)
                .compact();
    }

    public static String getEmailFromToken(String token) {
        Claims claims = parseClaims(token);
        return String.valueOf(claims.get("email"));
    }

    private static Claims parseClaims(String token) {
        // Remove "Bearer " prefix if present
        if (token.startsWith("Bearer ")) {
            token = token.substring(7);
        }
        return Jwts.parser()
                .verifyWith(key)
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }

    private static String populateAuthorities(Collection<? extends GrantedAuthority> authorities) {
        Set<String> auth = new HashSet<>();
        for (GrantedAuthority ga : authorities) {
            auth.add(ga.getAuthority());
        }
        return String.join(",", auth);
    }
}