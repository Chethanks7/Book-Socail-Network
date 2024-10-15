package com.Book_social_Network.email;

import com.Book_social_Network.email.EmailTemplateName;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.thymeleaf.context.Context;
import org.thymeleaf.spring6.SpringTemplateEngine;

import java.util.HashMap;
import java.util.Map;

import static java.nio.charset.StandardCharsets.UTF_8;

@Service
@RequiredArgsConstructor
public class EmailService {

    private final JavaMailSender mailSender;
    private final SpringTemplateEngine templateEngine;

    @Async
    public void sendEmail(
            String to,
            String username,
            EmailTemplateName emailTemplate,
            String confirmationUrl,
            String activationCode,
            String subject,
            int port // Accepting port as an argument
    ) throws MessagingException {

        // Create a new JavaMailSender instance with the given port
        JavaMailSender customMailSender = createCustomMailSender(port);

        String templateName = emailTemplate == null ? "confirm-email" : emailTemplate.getName();
        MimeMessage message = customMailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(
                message,
                MimeMessageHelper.MULTIPART_MODE_MIXED,
                UTF_8.name()
        );

        Map<String, Object> properties = new HashMap<>();
        properties.put("username", username);
        properties.put("confirmationUrl", confirmationUrl);
        properties.put("activation_code", activationCode);

        Context context = new Context();
        context.setVariables(properties);

        helper.setFrom("chethanks545@gmail.com");
        helper.setTo(to);
        helper.setSubject(subject);

        String html = templateEngine.process(templateName, context);
        helper.setText(html, true);

        customMailSender.send(message); // Use the custom mail sender
    }

    private JavaMailSender createCustomMailSender(int port) {
        // Create a JavaMailSender with a custom configuration
        // This is just a placeholder; implement the actual configuration
        return new JavaMailSenderImpl() {
            {
                setHost("localhost");
                setPort(port);
                setUsername("your-username"); // Add your username here
                setPassword("your-password"); // Add your password here
                // Add any additional properties if needed
            }
        };
    }
}
