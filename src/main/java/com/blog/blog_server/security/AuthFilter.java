package com.blog.blog_server.security;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import org.springframework.stereotype.Component;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.client.RestTemplate;
import com.blog.blog_server.model.AuthResponse;

@Component
public class AuthFilter extends OncePerRequestFilter {

    @Value("${auth.service.url}")
    private String authServiceUrl;

    @Autowired
    private RestTemplate restTemplate;

    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) {
        String path = request.getServletPath();
        return "/api/v1/trending".equals(path);
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
        String token = request.getHeader("Authorization");
        System.out.println(token);
        if (token == null || !token.startsWith("Bearer ")) {
            response.sendError(javax.servlet.http.HttpServletResponse.SC_UNAUTHORIZED,
                    "Missing or invalid Authorization header");
            return;
        }

        token = token.substring(7);

        try {
            String authUrl = authServiceUrl + "/validate?token=" + token;
            AuthResponse isValid = restTemplate.getForObject(authUrl, AuthResponse.class);

            assert isValid != null;
            if (Boolean.TRUE.equals(isValid.isValid())) {
                filterChain.doFilter(request, response);
            } else {
                response.sendError(javax.servlet.http.HttpServletResponse.SC_UNAUTHORIZED, "Invalid token");
            }

        } catch (Exception e) {
            response.sendError(javax.servlet.http.HttpServletResponse.SC_UNAUTHORIZED, "Authentication service error");
        }
    }
}
