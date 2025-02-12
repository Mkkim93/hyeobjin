package com.hyeobjin.application.admin.dto.calendar;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UpdateCalendarDTO {

    private Long calendarId;
    private String title;
    private String description;
    private LocalDateTime startTime;
    private LocalDateTime endTime;
    private LocalDateTime createAt;
    private LocalDateTime updateAt;
    private String location;
    private String calendarYN;
    private String scheduleStatus;
    private String holiday;
    private Long usersId;

}
