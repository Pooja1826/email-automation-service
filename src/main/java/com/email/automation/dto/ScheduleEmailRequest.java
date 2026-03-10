package com.email.automation.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ScheduleEmailRequest {

    private String to;
    private String subject;
    private String body;
    private LocalDateTime scheduledTime;
}
