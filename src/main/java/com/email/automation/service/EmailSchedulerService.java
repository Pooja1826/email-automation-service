package com.email.automation.service;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

@Service
public class EmailSchedulerService {

    private final EmailAutomationService service;

    public EmailSchedulerService(EmailAutomationService service) {
        this.service = service;
    }

    @Scheduled(fixedRate = 60000)
    public void scheduleEmail(){
        service.sendEmail("pooja.jain.tech18@gmail.com","Scheduled Email Test","If you received this, my email automation works with scheduler");
        System.out.println("Scheduling email at every 60s");
    }
}
