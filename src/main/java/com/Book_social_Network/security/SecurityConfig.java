package com.Book_social_Network.security;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration // Indicates that this class provides Spring configuration.
@EnableWebSecurity // Enables Spring Security's web security support.
@RequiredArgsConstructor // Generates a constructor for dependency injection.
@EnableMethodSecurity(securedEnabled = true) // Enables method-level security annotations.
public class SecurityConfig {

    private final JwtAuthFilter jwtAuthFilter; // JWT authentication filter.
    private final AuthenticationProvider authenticationProvider; // Custom authentication provider.

    /**
     * Configures the security filter chain for the application.
     *
     * @param http the HttpSecurity object for configuring web-based security.
     * @return the configured SecurityFilterChain.
     * @throws Exception if an error occurs while configuring security.
     */
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        return http
                .csrf(AbstractHttpConfigurer::disable) // Disables CSRF protection.
                .formLogin(Customizer.withDefaults()) // Enables form-based login with default settings.
                .httpBasic(Customizer.withDefaults()) // Enables HTTP Basic authentication with default settings.
                .authorizeHttpRequests(authorizeRequests ->
                        authorizeRequests.requestMatchers(
                                        "/auth/**",
                                        "/v2/api-docs",
                                        "/v3/api-docs",
                                        "/v3/api-docs/**",
                                        "/swagger-resources",
                                        "/swagger-resources/**",
                                        "/configuration/ui",
                                        "/configuration/security",
                                        "/swagger-ui/**",
                                        "/webjars/**",
                                        "/swagger-ui.html" // Permit access to specific request matchers (add actual paths).
                                ).permitAll() // Allows unauthenticated access to the specified paths.
                                .anyRequest()
                                .authenticated() // Requires authentication for all other requests.
                )
                .sessionManagement(sessionManagement ->
                        sessionManagement.sessionCreationPolicy(SessionCreationPolicy.STATELESS)) // Configures session management to be stateless.
                .authenticationProvider(authenticationProvider) // Sets the custom authentication provider.
                .addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class) // Adds the JWT authentication filter before the UsernamePasswordAuthenticationFilter.
                .build(); // Builds the SecurityFilterChain.
    }
}
