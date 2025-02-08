package com.hyeobjin.application.admin.dto.calendar;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
public class AdminCalendarSummaryDTO {

    private Long calendarId;
    private String title;
    private LocalDateTime createAt;
    private LocalDateTime startTime;
    private LocalDateTime endTime;

    public AdminCalendarSummaryDTO(Long calendarId, String title, LocalDateTime createAt,
                                   LocalDateTime startTime, LocalDateTime endTime) {
        this.calendarId = calendarId;
        this.title = title;
        this.createAt = createAt;
        this.startTime = startTime;
        this.endTime = endTime;
    }
}
