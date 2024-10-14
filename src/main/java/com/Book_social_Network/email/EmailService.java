package com.Book_social_Network.email;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.thymeleaf.context.Context;
import org.thymeleaf.spring6.SpringTemplateEngine;

import java.util.HashMap;
import java.util.Map;

import static java.nio.charset.StandardCharsets.UTF_8;

// Service class for sending emails
@Service
@RequiredArgsConstructor
public class EmailService {

    // Dependency to send emails
    private final JavaMailSender mailSender;
    // Dependency for processing email templates
    private final SpringTemplateEngine templateEngine;

    // Method to send an email
    @Async
    public void sendEmail(
            String to, // Recipient's email address
            String username, // Username of the recipient
            EmailTemplateName emailTemplate, // Template name for the email
            String confirmationUrl, // URL for email confirmation
            String activationCode, // Code for activation
            String subject // Subject of the email
    ) throws MessagingException { // Throws MessagingException if there are issues with sending the email

        // Determine the template name to use; default to "confirm-email" if none provided
        String templateName = emailTemplate == null ? "confirm-email" : emailTemplate.getName();

        // Create a new MimeMessage for the email
        MimeMessage message = mailSender.createMimeMessage();
        // Create a MimeMessageHelper to help with setting the email properties
        MimeMessageHelper helper = new MimeMessageHelper(
                message, // The MimeMessage to use
                MimeMessageHelper.MULTIPART_MODE_MIXED, // Mode for multipart messages
                UTF_8.name() // Character encoding
        );

        // Prepare the context variables for the email template
        Map<String, Object> properties = new HashMap<>();
        properties.put("username", username); // Add username to properties
        properties.put("confirmationUrl", confirmationUrl); // Add confirmation URL to properties
        properties.put("activation_code", activationCode); // Add activation code to properties

        // Create a Thymeleaf context and set the variables
        Context context = new Context();
        context.setVariables(properties);

        // Set the email sender, recipient, and subject
        helper.setFrom("chethanks545@gmail.com"); // Set the sender's email
        helper.setTo(to); // Set the recipient's email
        helper.setSubject(subject); // Set the email subject

        // Process the email template to generate HTML content
        String html = templateEngine.process(templateName, context); // Generate the HTML from the template
        helper.setText(html, true); // Set the HTML content of the email

        // Send the email using the JavaMailSender
        mailSender.send(message);
    }
}
