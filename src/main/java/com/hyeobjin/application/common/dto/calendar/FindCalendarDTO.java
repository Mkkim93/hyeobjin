package com.hyeobjin.application.common.dto.calendar;

import com.hyeobjin.domain.entity.calendar.enums.ScheduleStatus;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
public class FindCalendarDTO {

    private Long calenderId;
    private String findTitle;
    private LocalDateTime findStartTime;
    private LocalDateTime findEndTime;
    private LocalDateTime findCreateAt;
    private ScheduleStatus findScheduleStatus;
    private String calenderYN;

    public FindCalendarDTO(Long calenderId, String findTitle,
                           LocalDateTime findStartTime, LocalDateTime findEndTime,
                           LocalDateTime findCreateAt, ScheduleStatus findScheduleStatus
                          ) {
        this.calenderId = calenderId;
        this.findTitle = findTitle;
        this.findStartTime = findStartTime;
        this.findEndTime = findEndTime;
        this.findCreateAt = findCreateAt;
        this.findScheduleStatus = findScheduleStatus;
    }
}
