package com.training.banking.filter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
public class ApiKeyFilter extends OncePerRequestFilter {

    private static final String API_KEY = "secret-key-123";

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain)
            throws ServletException, IOException {

        String path = request.getRequestURI();

        // Public endpoints - no authentication needed
        if (path.startsWith("/api/public")) {
            filterChain.doFilter(request, response);
            return;
        }

        // Admin endpoints - require a valid API key
        if (path.startsWith("/api/admin")) {
            String apiKey = request.getHeader("X-API-KEY");

            if (apiKey == null || !API_KEY.equals(apiKey)) {
                response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                response.setContentType("application/json");
                response.getWriter().write(
                        "{\"error\": \"UNAUTHORIZED\", \"message\": \"Missing or invalid API key\"}"
                );
                return;
            }
        }

        // Key is valid (or path is not /api/admin) - let the request through
        filterChain.doFilter(request, response);
    }
}
