package com.email.automation.controller;

import com.email.automation.dto.EmailRequest;
import com.email.automation.dto.ScheduleEmailRequest;
import com.email.automation.service.EmailAutomationService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping({"/api/email/automation"})
public class EmailAutomationController {

    private final EmailAutomationService service;

    public EmailAutomationController(EmailAutomationService service) {
        this.service = service;
    }

    //send email immediately
    @PostMapping("/send")
    public String sendEmail(@RequestBody EmailRequest emailRequest) {
        service.sendEmail(emailRequest.getTo(), emailRequest.getSubject(), emailRequest.getBody());
        return "Email Sent Successfully";
    }

    //saving data to send email on specific time
    @PostMapping("/schedule")
    public String scheduleEmail(@RequestBody ScheduleEmailRequest scheduledEmail) {
        service.scheduleEmail(scheduledEmail);
        return "Email Data Saved in DB";
    }
}
