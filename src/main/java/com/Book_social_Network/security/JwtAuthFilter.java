package com.Book_social_Network.security;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.lang.NonNull;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Service;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Service // Indicates that this class is a service component in the Spring context.
@RequiredArgsConstructor // Generates a constructor for dependency injection.
public class JwtAuthFilter extends OncePerRequestFilter {

    private final JwtService jwtService; // Service for handling JWT operations.
    private final UserDetailsService userDetailsService; // Service for loading user details.

    /**
     * Filters incoming requests and checks for a valid JWT token.
     *
     * @param request   the HTTP request.
     * @param response  the HTTP response.
     * @param filterChain the filter chain for further processing.
     * @throws ServletException if a servlet error occurs.
     * @throws IOException if an I/O error occurs.
     */
    @Override
    protected void doFilterInternal(
            @NonNull HttpServletRequest request,
            @NonNull HttpServletResponse response,
            @NonNull FilterChain filterChain
    ) throws ServletException, IOException {

        // Bypass authentication for the authentication endpoint.
        if (request.getServletPath().contains("/auth/v1/auth")) {
            filterChain.doFilter(request, response);
            return;
        }

        // Extract the Authorization header from the request.
        final String authHeader = request.getHeader(HttpHeaders.AUTHORIZATION);
        final String jwt;
        final String userEmail;

        // Check if the Authorization header is present and starts with "Bearer ".
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            filterChain.doFilter(request, response);
            return; // If not valid, continue to the next filter.
        }

        // Extract the JWT from the header.
        jwt = authHeader.substring(7); // Remove "Bearer " prefix.
        userEmail = jwtService.extractUsername(jwt); // Extract username from the JWT.

        // If the userEmail is not null and no authentication is present, proceed.
        if (userEmail != null && SecurityContextHolder.getContext().getAuthentication() == null) {
            // Load user details based on the email extracted from the JWT.
            UserDetails userDetails = userDetailsService.loadUserByUsername(userEmail);
            // Validate the token with the user details.
            if (jwtService.isTokenValid(jwt, userDetails)) {
                // Create an authentication token if the token is valid.
                UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(
                        userDetails, null, userDetails.getAuthorities()
                );
                token.setDetails(new WebAuthenticationDetailsSource().buildDetails(request)); // Set request details.

                // Set the authentication in the SecurityContext.
                SecurityContextHolder.getContext().setAuthentication(token);
            }
        }

        // Continue with the next filter in the chain.
        filterChain.doFilter(request, response);
    }
}
