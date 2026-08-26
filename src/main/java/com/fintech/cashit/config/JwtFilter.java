package com.fintech.cashit.config;

import com.fintech.cashit.service.JwtService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

public class JwtFilter extends OncePerRequestFilter {
    @Autowired
    private JwtService jwtService;


    @Override
    protected void doFilterInternal(
            HttpServletRequest request,
            HttpServletResponse response,
            FilterChain filterChain) throws ServletException, IOException {

        filterChain.doFilter(request, response);
        String authHeader=request.getHeader("Authorization");
        String token=authHeader.substring(7);
        String email = jwtService.extractEmail(token);

        if(authHeader==null || !authHeader.startsWith("Bearer ")){
            return;

        }
    }
}
