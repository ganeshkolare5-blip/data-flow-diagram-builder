package com.internship.tool.service;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.mail.javamail.JavaMailSender;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import java.util.HashMap;
import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class EmailServiceTest {

    @Mock
    private JavaMailSender emailSender;

    @Mock
    private TemplateEngine templateEngine;

    @Mock
    private MimeMessage mimeMessage;

    @InjectMocks
    private EmailService emailService;

    @BeforeEach
    void setUp() {
        // We use reflection to set the private @Value field since we're not using SpringRunner
        try {
            java.lang.reflect.Field field = EmailService.class.getDeclaredField("fromEmail");
            field.setAccessible(true);
            field.set(emailService, "noreply@example.com");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    void testSendEmailWithTemplate_Success() throws MessagingException {
        Map<String, Object> model = new HashMap<>();
        model.put("name", "Test User");

        when(templateEngine.process(anyString(), any(Context.class))).thenReturn("<html>Test</html>");
        when(emailSender.createMimeMessage()).thenReturn(mimeMessage);

        emailService.sendEmailWithTemplate("to@example.com", "Subject", "template", model);

        verify(emailSender, times(1)).send(any(MimeMessage.class));
    }

    @Test
    void testSendEmailWithTemplate_Failure() {
        Map<String, Object> model = new HashMap<>();
        
        when(templateEngine.process(anyString(), any(Context.class))).thenThrow(new RuntimeException("Template error"));

        // Should catch the exception and log it (we verify by no exception thrown from method)
        assertDoesNotThrow(() -> {
            emailService.sendEmailWithTemplate("to@example.com", "Subject", "template", model);
        });
    }
}
