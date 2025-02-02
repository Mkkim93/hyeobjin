package com.hyeobjin.application.admin.dto.calendar;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
public class AdminCalendarSummaryDTO {

    private Long calenderId;
    private String title;
    private LocalDateTime createAt;
    private LocalDateTime startTime;
    private LocalDateTime endTime;

    public AdminCalendarSummaryDTO(Long calenderId, String title, LocalDateTime createAt,
                                LocalDateTime startTime, LocalDateTime endTime) {
        this.calenderId = calenderId;
        this.title = title;
        this.createAt = createAt;
        this.startTime = startTime;
        this.endTime = endTime;
    }
}
