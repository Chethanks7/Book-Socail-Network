package com.Book_social_Network.app_config;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.AuditorAware;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
@Getter
@Setter

public class Config {

    private final UserDetailsService userDetailsService; // UserDetailsService for loading user-specific data.

    public Config(UserDetailsService userDetailsService) {
        this.userDetailsService = userDetailsService;
    }

    /**
     * Configures an AuthenticationProvider that uses DaoAuthenticationProvider.
     * This provider is responsible for authenticating users using the UserDetailsService.
     *
     * @return the configured AuthenticationProvider.
     */
    @Bean // Indicates that this method produces a bean to be managed by the Spring container.
    public AuthenticationProvider authenticationProvider() {

        DaoAuthenticationProvider provider = new DaoAuthenticationProvider(); // Creates an instance of DaoAuthenticationProvider.

        provider.setUserDetailsService(userDetailsService); // Sets the UserDetailsService to be used by the provider.
        provider.setPasswordEncoder(passwordEncoder()); // Sets the password encoder for encoding passwords.

        return provider; // Returns the configured AuthenticationProvider.
    }

    @Bean
    public JavaMailSender mailSender() {
        return new JavaMailSenderImpl() ;
    }

    /**
     * Configures a PasswordEncoder bean to encode passwords using BCrypt.
     *
     * @return a PasswordEncoder instance.
     */
    @Bean // Indicates that this method produces a bean to be managed by the Spring container.
    public PasswordEncoder passwordEncoder() {

        return new BCryptPasswordEncoder(); // Returns a new instance of BCryptPasswordEncoder for password hashing.
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration configuration) throws Exception {
        return configuration.getAuthenticationManager();
    }

    @Bean
    public AuditorAware<Integer> auditorAware(){
        return new ApplicationAuditAware();
    }
}
