package com.internship.tool.controller;

import com.internship.tool.service.EmailService;
import com.internship.tool.service.ScheduledTasks;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/test-email")
@RequiredArgsConstructor
public class TestEmailController {

    private final ScheduledTasks scheduledTasks;
    private final EmailService emailService;

    @GetMapping("/run-scheduled-tasks")
    public ResponseEntity<String> triggerScheduledTasks() {
        scheduledTasks.sendDailyReminder();
        scheduledTasks.sendDeadlineAlerts();
        return ResponseEntity.ok("Triggered daily reminder and deadline alerts manually. Check console and inbox.");
    }

    @GetMapping("/send-direct")
    public ResponseEntity<String> sendDirectEmail(@RequestParam String email) {
        Map<String, Object> model = new HashMap<>();
        model.put("diagramName", "Test Diagram from Controller");
        model.put("description", "This is a direct test to verify email config works.");
        
        emailService.sendEmailWithTemplate(
                email,
                "Test Email Delivery",
                "daily-reminder",
                model
        );
        return ResponseEntity.ok("Direct test email sent to: " + email);
    }
}
