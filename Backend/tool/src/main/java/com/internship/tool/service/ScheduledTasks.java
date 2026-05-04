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

    // Daily reminder at 9:00 AM - Grouped by User
    @Scheduled(cron = "0 0 9 * * ?")
    public void sendDailyReminder() {
        log.info("Running daily reminder task - Grouped by User");
        
        List<String> userEmails = diagramRepository.findDistinctUserEmails();
        
        for (String email : userEmails) {
            List<Diagram> userDiagrams = diagramRepository.findByUserEmail(email);
            if (!userDiagrams.isEmpty()) {
                Map<String, Object> model = new HashMap<>();
                model.put("userName", email); // or extract from User entity if available
                model.put("diagrams", userDiagrams);
                
                emailService.sendEmailWithTemplate(
                        email,
                        "Daily Summary: You have " + userDiagrams.size() + " diagrams",
                        "daily-summary",
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
