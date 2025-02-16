package com.hyeobjin.application.common.dto.calendar;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
public class FindCalendarDTO {

    private Long calendarId;
    private String findTitle;
    private LocalDateTime findStartTime;
    private LocalDateTime findEndTime;
    private LocalDateTime findCreateAt;

    public FindCalendarDTO(Long calendarId, String findTitle,
                           LocalDateTime findStartTime, LocalDateTime findEndTime,
                           LocalDateTime findCreateAt
                          ) {
        this.calendarId = calendarId;
        this.findTitle = findTitle;
        this.findStartTime = findStartTime;
        this.findEndTime = findEndTime;
        this.findCreateAt = findCreateAt;
    }
}
