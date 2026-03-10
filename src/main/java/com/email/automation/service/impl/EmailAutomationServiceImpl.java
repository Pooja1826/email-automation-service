package com.email.automation.service.impl;

import com.email.automation.dto.ScheduleEmailRequest;
import com.email.automation.entity.ScheduledEmail;
import com.email.automation.repository.ScheduledEmailRepository;
import com.email.automation.service.EmailAutomationService;
import com.email.automation.util.EmailStatus;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;


@Service
public class EmailAutomationServiceImpl implements EmailAutomationService {

    private final JavaMailSender mailSender;
    private final ScheduledEmailRepository repository;

    @Value("${spring.mail.username}")
    private String fromEmail;

    public EmailAutomationServiceImpl(JavaMailSender mailSender, ScheduledEmailRepository repository) {
        this.mailSender = mailSender;
        this.repository = repository;
    }


    @Override
    @Async("emailExecutor")
    public void sendEmail(String to, String subject, String body) {
        //for checking retry mechanism added if loop
//        if(true){
//            throw new RuntimeException("Simulated failure");
//        }
        System.out.println("Thread Executing Currently: " + Thread.currentThread().getName());
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom(fromEmail);
        message.setTo(to);
        message.setSubject(subject);
        message.setText(body);
        message.setCc("jpooja162000@gmail.com");
        mailSender.send(message);

    }

    @Override
    public void scheduleEmail(ScheduleEmailRequest scheduledEmail) {
        ScheduledEmail pendingEmail = ScheduledEmail.builder().toEmail(scheduledEmail.getTo()).subject(scheduledEmail.getSubject()).body(scheduledEmail.getBody()).scheduledTime(scheduledEmail.getScheduledTime()).status(EmailStatus.PENDING.name()).retryCount(0).maxRetry(3).build();
        repository.save(pendingEmail);
    }
}
