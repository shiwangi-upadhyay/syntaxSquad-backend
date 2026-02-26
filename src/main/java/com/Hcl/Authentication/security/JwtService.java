package com.Hcl.Authentication.security;

import com.Hcl.Authentication.entity.User;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.security.Key;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class JwtService {

    @Value("${jwt.secret}")
    private String secret;

    @Value("${jwt.expiration}")
    private long expiration;

    private Key getSignKey() {
        byte[] keyBytes = Decoders.BASE64.decode(secret);
        return Keys.hmacShaKeyFor(keyBytes);
    }

    public String generateToken(User user) {
        return Jwts.builder()
                .setSubject(user.getEmail())
                .claim("roles", user.getRoles()
                        .stream()
                        .map(r -> r.getName().name())
                        .collect(Collectors.toList()))
                .claim("userId", user.getId())
                .claim("userName", user.getName())
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + expiration))
                .signWith(getSignKey(), SignatureAlgorithm.HS256)
                .compact();
    }

    public Claims extractClaimsIfValid(String token) {
        token = extractTokenFromHeader(token);
        if (token == null || token.isEmpty()) {
            return null;
        }
        try {
            Claims claims = Jwts.parserBuilder()
                    .setSigningKey(getSignKey())
                    .build()
                    .parseClaimsJws(token)
                    .getBody();
            if (claims.getExpiration().before(new Date())) {
                return null;
            }
            return claims;
        } catch (Exception e) {
            return null;
        }
    }

    private String extractTokenFromHeader(String authHeader) {
        if (authHeader != null && authHeader.startsWith("Bearer ")) {
            return authHeader.substring(7);
        }
        return authHeader;
    }

    public String extractUsername(String token) {
        Claims claims = extractClaimsIfValid(token);
        return claims != null ? claims.getSubject() : null;
    }

    @SuppressWarnings("unchecked")
    public List<String> extractRoles(String token) {
        Claims claims = extractClaimsIfValid(token);
        return claims != null ? claims.get("roles", List.class) : null;
    }

    public boolean validateToken(String token) {
        return extractClaimsIfValid(token) != null;
    }
}
