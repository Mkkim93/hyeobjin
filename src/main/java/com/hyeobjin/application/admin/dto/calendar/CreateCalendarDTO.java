package com.hyeobjin.application.admin.dto.calendar;

import com.hyeobjin.domain.entity.calendar.Calendar;
import com.hyeobjin.domain.entity.calendar.enums.ScheduleStatus;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.commons.lang3.EnumUtils;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
public class CreateCalendarDTO {

    private String title;
    private String description;
    private String location;
    private String scheduleStatus;
    private String calendarYN;
    private Boolean holidays;

    private LocalDateTime createAt; // 일정 등록일

    private LocalDateTime startTime; // 일정 시작일
    private LocalDateTime endTime; // 일정 종료일

    private Long usersId;

    public Calendar saveToEntity(CreateCalendarDTO createCalendarDTO) {
       return Calendar.builder()
                .title(createCalendarDTO.getTitle())
                .description(createCalendarDTO.getDescription())
                .startTime(createCalendarDTO.getStartTime())
                .endTime(createCalendarDTO.getEndTime())
                .location(createCalendarDTO.getLocation())
                .scheduleStatus(EnumUtils.getEnum(ScheduleStatus.class, createCalendarDTO.getScheduleStatus()))
                .calenderYN(createCalendarDTO.getCalendarYN())
               .holidays(createCalendarDTO.getHolidays())
                .usersId(createCalendarDTO.getUsersId())
                .build();
    }
}
