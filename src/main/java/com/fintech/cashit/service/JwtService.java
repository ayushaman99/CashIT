package com.fintech.cashit.service;

import io.jsonwebtoken.Jwt;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.stereotype.Service;

@Service
public class JwtService {

    public String generateToken(String email)
    {
        return Jwts.builder()
                .subject(email)
                .signWith(
                        Keys.hmacShaKeyFor("my-secret-key-my-secret-key-my-secret-key".getBytes())
                )
                .compact();
    }
    public String extractEmail(String token){
        return Jwts.parser()
                .verifyWith(Keys.hmacShaKeyFor("my-secret-key-my-secret-key-my-secret-key".getBytes()))
                .build()
                .parseSignedClaims(token)
                .getPayload()
                .getSubject();
    }
}
