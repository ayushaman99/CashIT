package com.fintech.cashit.config;

import com.fintech.cashit.entity.User;
import com.fintech.cashit.repository.UserRepository;
import com.fintech.cashit.service.JwtService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;

import java.io.IOException;
import java.util.ArrayList;

public class JwtFilter extends OncePerRequestFilter {
    @Autowired
    private JwtService jwtService;
    @Autowired
    private UserRepository userRepository;


    @Override
    protected void doFilterInternal(
            HttpServletRequest request,
            HttpServletResponse response,
            FilterChain filterChain) throws ServletException, IOException {

        filterChain.doFilter(request, response);
        String authHeader=request.getHeader("Authorization");
        String token=authHeader.substring(7);
        String email = jwtService.extractEmail(token);
        User user=userRepository.findByEmail(email);

        if(user==null)
        {
            filterChain.doFilter(request,response);
            return;
        }
        if(user!=null)
        {
            UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(user,null,new ArrayList<>());
            SecurityContextHolder.getContext().setAuthentication(authentication);
            filterChain.doFilter(request,response);
        }

        if(authHeader==null || !authHeader.startsWith("Bearer ")){
            return;

        }
    }
}
