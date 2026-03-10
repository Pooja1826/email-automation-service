package com.email.automation.service;

import com.email.automation.dto.ScheduleEmailRequest;

public interface EmailAutomationService {

    void sendEmail(String to, String subject, String body);

    void scheduleEmail(ScheduleEmailRequest scheduledEmail);
}
