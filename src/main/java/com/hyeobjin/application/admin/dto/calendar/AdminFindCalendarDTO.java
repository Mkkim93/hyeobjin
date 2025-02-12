package com.hyeobjin.application.admin.dto.calendar;

import com.hyeobjin.domain.entity.calendar.Calendar;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 상세 조회를 위한 DTO
 */
@Data
@NoArgsConstructor
public class AdminFindCalendarDTO {

    private Long calendarId;
    private String title;
    private LocalDateTime startTime;
    private LocalDateTime endTime;
    private String description;
    private LocalDateTime createAt;
    private LocalDateTime updateAt;
    private String location;
    private String calendarYN;
    private Long usersId;
    private String writer;
    private String holiday;

    public AdminFindCalendarDTO(Long calendarId, String title, LocalDateTime startTime,
                                LocalDateTime endTime, String description, LocalDateTime createAt,
                                LocalDateTime updateAt, String location,String holiday, String calendarYN,
                                Long usersId, String writer ) {
        this.calendarId = calendarId;
        this.title = title;
        this.startTime = startTime;
        this.endTime = endTime;
        this.description = description;
        this.createAt = createAt;
        this.updateAt = updateAt;
        this.location = location;
        this.holiday = holiday;
        this.calendarYN = calendarYN;
        this.usersId = usersId;
        this.writer = writer;
    }

    public List<AdminFindCalendarDTO> toDto(List<Calendar> calendars) {
        return calendars.stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }

    private AdminFindCalendarDTO convertToDto(Calendar calendar) {
        return new AdminFindCalendarDTO(
                calendar.getId(),
                calendar.getTitle(),
                calendar.getStartTime(),
                calendar.getEndTime(),
                calendar.getDescription(),
                calendar.getCreateAt(),
                calendar.getUpdateAt(),
                calendar.getLocation(),
                calendar.getHolidays(),
                calendar.getCalenderYN(),
                calendar.getUsers().getId(),
                calendar.getUsers().getName()
        );
    }
}
