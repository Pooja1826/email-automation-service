package com.email.automation.repository;

import com.email.automation.entity.ScheduledEmail;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.List;

public interface ScheduledEmailRepository extends JpaRepository<ScheduledEmail, Long> {

    //SELECT * FROM scheduled_email WHERE status = ? AND scheduled_time <= ?
    public List<ScheduledEmail> findByStatusAndScheduledTimeLessThanEqual(String status, LocalDateTime time);

    //SELECT * FROM scheduled_email WHERE status IN ('PENDING','FAILED') AND scheduled_time <= now;
    public List<ScheduledEmail> findByStatusInAndScheduledTimeLessThanEqual(List<String> status, LocalDateTime time);

}
