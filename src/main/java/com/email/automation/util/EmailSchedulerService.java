package com.email.automation.util;

import com.email.automation.entity.ScheduledEmail;
import com.email.automation.repository.ScheduledEmailRepository;
import com.email.automation.service.EmailAutomationService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

@Service
@Slf4j
public class EmailSchedulerService {

    private final EmailAutomationService service;
    private final ScheduledEmailRepository repository;

    public EmailSchedulerService(EmailAutomationService service, ScheduledEmailRepository repository) {
        this.service = service;
        this.repository = repository;
    }


//    @Scheduled(fixedRate = 60000)
//    public void scheduleEmail() {
//        service.sendEmail("pooja.jain.tech18@gmail.com", "Scheduled Email Test", "If you received this, my email automation works with scheduler");
//        System.out.println("Scheduling email at every 60s");
//    }

    @Scheduled(fixedRate = 60000)
    public void scheduleEmail() {
        LocalDateTime now = LocalDateTime.now();
        log.info("Scheduler running at : " + now);
        List<String> statuses = Arrays.asList(EmailStatus.PENDING.name(), EmailStatus.FAILED.name());
//        List<ScheduledEmail> pendingEmailList = repository.findByStatusAndScheduledTimeLessThanEqual("PENDING", now);
        List<ScheduledEmail> pendingEmailList = repository.findByStatusInAndScheduledTimeLessThanEqual(statuses, now);
        if (!pendingEmailList.isEmpty()) {
            for (ScheduledEmail email : pendingEmailList) {
                if (email.getRetryCount() >= email.getMaxRetry()) {
                    log.warn("Max Retry reached for email id: {}", email.getToEmail());
                    email.setStatus(EmailStatus.FAILED_PERMANENT.name());
                    repository.save(email);
                    continue;
                }
                try {
                    service.sendEmail(email.getToEmail(), email.getSubject(), email.getBody());
                    email.setStatus(EmailStatus.SENT.name());
                } catch (Exception e) {
                    email.setRetryCount(email.getRetryCount() + 1);
                    email.setStatus(EmailStatus.FAILED.name());
                    email.setLastAttemptTime(now);
                }

                repository.save(email);
            }
        }
    }
}
