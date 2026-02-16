package com.email.automation.service.impl;

import com.email.automation.service.EmailAutomationService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;


@Service
public class EmailAutomationServiceImpl implements EmailAutomationService {

    private final JavaMailSender mailSender;

    @Value("${spring.mail.username}")
    private String fromEmail;
    public EmailAutomationServiceImpl(JavaMailSender mailSender){
        this.mailSender = mailSender;
    }

    @Override
    public void sendEmail(String to, String subject, String body) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom(fromEmail);
        message.setTo(to);
        message.setSubject(subject);
        message.setText(body);
        message.setCc("jpooja162000@gmail.com");
        mailSender.send(message);

    }
}
