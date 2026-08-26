package com.fintech.cashit.service;

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
}
