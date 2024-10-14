package com.Book_social_Network.auth;

import com.Book_social_Network.email.EmailService;
import com.Book_social_Network.email.EmailTemplateName;
import com.Book_social_Network.role.RoleRepository;
import com.Book_social_Network.user.Token;
import com.Book_social_Network.user.TokenRepository;
import com.Book_social_Network.user.User;
import com.Book_social_Network.user.UserRepository;
import jakarta.mail.MessagingException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.security.SecureRandom;
import java.time.LocalDateTime;
import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class AuthenticationService {

    // Repositories and services are injected via constructor injection.
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;
    private final TokenRepository tokenRepository;
    private final EmailService emailService ;


    @Value("${application.mailing.frontend.activation-url}")
    private String activationUrl;

    /**
     * Registers a new user by creating their account and assigning them the "USER" role.
     * This also triggers the process to send a validation email.
     */
    public void register(RegistrationRequest request) throws MessagingException {

        // Fetch the "USER" role from the RoleRepository, throw an exception if not found.
        var role = roleRepository.findByName("USER").orElseThrow(
                () -> new IllegalStateException("Role not initialized")
        );

        // Build a new User object using the provided registration details.
        var user = User.builder()
                .firstname(request.getFirstname())
                .lastname(request.getLastname())
                .email(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword())) // Encrypt the password
                .accountLocked(false)
                .enabled(false) // Set to false until the user verifies their email.
                .roles(List.of(role)) // Assign the "USER" role.
                .build();

        // Save the user to the database.
        userRepository.save(user);

        // Send a validation email to the user to complete registration.
        sendValidationEmail(user);
    }

    /**
     * Sends a validation email to the newly registered user.
     */
    private void sendValidationEmail(User user) throws MessagingException {
        // Generate and save an activation token for email verification.
        var newToken = generateAndSaveActivationToken(user);
        // Code to send the email would go here.
        emailService.sendEmail(
                user.getEmail(),
                user.fullName(),
                EmailTemplateName.ACTIVATE_ACCOUNT,
                activationUrl,
                newToken,
                "account activation"
        );

    }

    /**
     * Generates an activation token and associates it with the user.
     * The token is valid for 15 minutes.
     *
     * @param user The user object to associate the token with.
     * @return The generated activation code.
     */
    private String generateAndSaveActivationToken(User user) {
        // Generate a 6-digit activation code.
        String generateCode = generateActivationCode(6);

        // Create a new Token object with the generated code and expiration time.
        var token = Token.builder()
                .token(generateCode)
                .createdAt(LocalDateTime.now()) // Set the creation time.
                .expiresAt(LocalDateTime.now().plusMinutes(15)) // Set the expiration time to 15 minutes from now.
                .user(user) // Associate the token with the user.
                .build();

        // Save the token (implementation omitted for simplicity).
         tokenRepository.save(token);
        return generateCode;
    }

    /**
     * Generates a random numeric activation code of the given length.
     *
     * @param length The length of the activation code.
     * @return The generated activation code as a string.
     */
    private String generateActivationCode(int length) {
        // Define the characters to use for generating the activation code (only digits).
        String characters = "0123456789";
        StringBuilder activationCode = new StringBuilder();
        SecureRandom random = new SecureRandom();

        // Generate a random string of the specified length.
        for (int i = 0; i < length; i++) {
            int randomChar = random.nextInt(characters.length());
            activationCode.append(characters.charAt(randomChar));
        }

        return activationCode.toString();
    }
}