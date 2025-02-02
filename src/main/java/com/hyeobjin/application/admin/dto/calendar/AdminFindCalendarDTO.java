package com.hyeobjin.application.admin.dto.calendar;

import com.hyeobjin.domain.entity.calendar.Calendar;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

/**
 * 상세 조회를 위한 DTO
 */
@Data
@NoArgsConstructor
public class AdminFindCalendarDTO {

    private Long calenderId;
    private String title;
    private String description;
    private LocalDateTime startTime;
    private LocalDateTime endTime;
    private LocalDateTime createAt;
    private LocalDateTime updateAt;
    private String location;
    private String calendarYN;
    private Long usersId;
    private String writer;

    public AdminFindCalendarDTO toDto(Calendar calendar) {
        this.calenderId = calendar.getId();
        this.title = calendar.getTitle();
        this.description = calendar.getDescription();
        this.createAt = calendar.getCreateAt();
        this.startTime = calendar.getStartTime();
        this.endTime = calendar.getEndTime();
        this.updateAt = calendar.getUpdateAt();
        this.location = calendar.getLocation();
        this.calendarYN = calendar.getCalenderYN();
        this.usersId = calendar.getUsers().getId();
        this.writer = calendar.getUsers().getName();
        return this;
    }
}
