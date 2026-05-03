package com.internship.tool.service;

import com.internship.tool.entity.Diagram;
import com.internship.tool.repository.DiagramRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class ScheduledTasksTest {

    @Mock
    private DiagramRepository diagramRepository;

    @Mock
    private EmailService emailService;

    @InjectMocks
    private ScheduledTasks scheduledTasks;

    @Test
    void testSendDailyReminder() {
        Diagram diagram = Diagram.builder()
                .name("Diagram 1")
                .userEmail("user@example.com")
                .build();
        
        when(diagramRepository.findAll()).thenReturn(Collections.singletonList(diagram));

        scheduledTasks.sendDailyReminder();

        verify(emailService, times(1)).sendEmailWithTemplate(
                eq("user@example.com"),
                anyString(),
                eq("daily-reminder"),
                any(Map.class)
        );
    }

    @Test
    void testSendDeadlineAlerts() {
        Diagram diagram = Diagram.builder()
                .name("Urgent Diagram")
                .userEmail("user@example.com")
                .deadline(LocalDateTime.now().plusHours(10))
                .build();

        when(diagramRepository.findByDeadlineBetween(any(LocalDateTime.class), any(LocalDateTime.class)))
                .thenReturn(Collections.singletonList(diagram));

        scheduledTasks.sendDeadlineAlerts();

        verify(emailService, times(1)).sendEmailWithTemplate(
                eq("user@example.com"),
                anyString(),
                eq("deadline-alert"),
                any(Map.class)
        );
    }
}
