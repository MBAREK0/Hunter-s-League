package com.mbarek0.web.huntersleague.filter;

import com.mbarek0.web.huntersleague.model.enums.Role;
import com.mbarek0.web.huntersleague.service.JwtService;
import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.FilterConfig;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class JwtAuthenticationFilter implements Filter {

    private final JwtService jwtService;  // Assume JwtService handles token parsing and validation

    public JwtAuthenticationFilter(JwtService jwtService) {
        this.jwtService = jwtService;
    }

    @Override
    public void doFilter(jakarta.servlet.ServletRequest request, jakarta.servlet.ServletResponse response, FilterChain chain)
            throws IOException, ServletException {
        HttpServletRequest httpRequest = (HttpServletRequest) request;
        HttpServletResponse httpResponse = (HttpServletResponse) response;

        String requestURI = httpRequest.getRequestURI();
        if (requestURI.startsWith("/api/v1/auth/")) {
            chain.doFilter(request, response);
            return;
        }

        String authHeader = httpRequest.getHeader("Authorization");

        if (authHeader != null && authHeader.startsWith("Bearer ")) {
            String token = authHeader.substring(7);
            if (jwtService.validateToken(token)) {

                String username = jwtService.extractUsername(token);
                Role role = jwtService.extractRole(username);

                if (isAuthorized(role, requestURI)) {
                    httpRequest.setAttribute("role", role);
                } else {
                    httpResponse.setStatus(HttpServletResponse.SC_FORBIDDEN);
                    return;
                }

                if (jwtService.isTokenValid(token, username)) {
                    httpRequest.setAttribute("username", username);

                } else {
                    httpResponse.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                    return;
                }

            } else {
                httpResponse.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                return;
            }
        } else {
            httpResponse.setStatus(HttpServletResponse.SC_FORBIDDEN);
            return;
        }

        chain.doFilter(request, response);
    }

    private boolean isAuthorized(Role role, String requestURI) {

        if (role.equals(Role.ADMIN)) {
            return true;
        }

        if (requestURI.startsWith("/api/member") && role.equals(Role.MEMBER)) {
            return true;
        }

        if ((requestURI.startsWith("/api/jury") || requestURI.startsWith("/api/member")) && role.equals(Role.JURY)) {
            return true;
        }
        return false;
    }

    @Override
    public void init(FilterConfig filterConfig) {
    }

    @Override
    public void destroy() {
    }
}
