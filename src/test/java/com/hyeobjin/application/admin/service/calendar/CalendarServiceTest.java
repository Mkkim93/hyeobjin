package com.hyeobjin.application.admin.service.calendar;

import com.hyeobjin.application.admin.dto.calendar.CreateCalendarDTO;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDateTime;

@Slf4j
@SpringBootTest
class CalendarServiceTest {

    @Autowired
    AdminCalendarService calendarService;

    @Test
    @DisplayName("일정 등록 테스트")
    void saveCalendar() {
        CreateCalendarDTO createCalendarDTO = new CreateCalendarDTO();
        createCalendarDTO.setTitle("일정 제목 테스트2");
        createCalendarDTO.setDescription("일정 내용 테스트2");
        createCalendarDTO.setLocation("대전 어딘가2");
        createCalendarDTO.setStartTime(LocalDateTime.of(2025, 02, 01, 12, 02));
        createCalendarDTO.setEndTime(LocalDateTime.of(2024, 02, 02, 15, 00));
        createCalendarDTO.setUsersId(1L);
        createCalendarDTO.setScheduleStatus("PLANNED");
        calendarService.save(createCalendarDTO);
    }
}