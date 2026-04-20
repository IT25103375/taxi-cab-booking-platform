package com.taxiandcabservice.auth;

import com.taxiandcabservice.repositories.AuthEntityRepository;
import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.jspecify.annotations.NullMarked;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
@Profile("!dev")
public class JwtAuthFilter extends OncePerRequestFilter {

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private AuthEntityRepository authEntityRepository;

    @Override
    @NullMarked
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain chain)
            throws ServletException, IOException {

        // Get authorization header
        String authHeader = request.getHeader("Authorization");

        // Token validation
        if (authHeader == null || !authHeader.startsWith("Bearer ") ||
                !jwtUtil.validateToken(authHeader.substring(7))) {
            HttpResponseResolver(response, response);
            return;
        }
        else {
            // Authorized
            authEntityRepository.findByEmail(
                    jwtUtil.extractEmail(authHeader.substring(7))).ifPresentOrElse(
                            authEntity -> SecurityContextHolder.getContext().setAuthentication(
                                    new UsernamePasswordAuthenticationToken(authEntity, null, authEntity.getAuthorities())
                            ),
                            () -> HttpResponseResolver(response, response)
                            );
        }

        chain.doFilter(request, response);
    }

    private void HttpResponseResolver(HttpServletResponse httpResponse, ServletResponse response) {
        httpResponse.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        try {
            response.getWriter().write("Unauthorized");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) {
        String path = request.getServletPath();

        return path.startsWith("/api/user/auth/");
    }
}
