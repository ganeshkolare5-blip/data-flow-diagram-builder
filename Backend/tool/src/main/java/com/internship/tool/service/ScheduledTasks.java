package com.internship.tool.service;

import com.internship.tool.entity.Diagram;
import com.internship.tool.repository.DiagramRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
@RequiredArgsConstructor
@Slf4j
public class ScheduledTasks {

    private final DiagramRepository diagramRepository;
    private final EmailService emailService;

    // Daily reminder at 9:00 AM
    @Scheduled(cron = "0 0 9 * * ?")
    public void sendDailyReminder() {
        log.info("Running daily reminder task");
        // For demonstration, we could find diagrams created recently or just notify users to review their diagrams.
        // Or if there's a daily task, implement logic here.
        // Assuming we want to send a reminder to users who have active diagrams.
        // Because we don't have a specific "active" status, let's just find all diagrams and notify their users (simplified).
        // A better approach is to send one summary email per user, but here's a per-diagram example:
        List<Diagram> allDiagrams = diagramRepository.findAll();
        for (Diagram diagram : allDiagrams) {
            if (diagram.getUserEmail() != null && !diagram.getUserEmail().isEmpty()) {
                Map<String, Object> model = new HashMap<>();
                model.put("diagramName", diagram.getName());
                model.put("description", diagram.getDescription() != null ? diagram.getDescription() : "No description provided");
                
                emailService.sendEmailWithTemplate(
                        diagram.getUserEmail(),
                        "Daily Reminder: Check your Diagram '" + diagram.getName() + "'",
                        "daily-reminder",
                        model
                );
            }
        }
    }

    // Deadline alert every hour checking for deadlines in the next 24 hours
    @Scheduled(cron = "0 0 * * * ?")
    public void sendDeadlineAlerts() {
        log.info("Running deadline alert task");
        LocalDateTime now = LocalDateTime.now();
        LocalDateTime next24Hours = now.plusHours(24);

        List<Diagram> upcomingDeadlines = diagramRepository.findByDeadlineBetween(now, next24Hours);
        
        for (Diagram diagram : upcomingDeadlines) {
            if (diagram.getUserEmail() != null && !diagram.getUserEmail().isEmpty()) {
                Map<String, Object> model = new HashMap<>();
                model.put("diagramName", diagram.getName());
                model.put("deadline", diagram.getDeadline().toString());
                
                emailService.sendEmailWithTemplate(
                        diagram.getUserEmail(),
                        "Urgent: Deadline Approaching for '" + diagram.getName() + "'",
                        "deadline-alert",
                        model
                );
            }
        }
    }
}
