package com.hyeobjin.application.admin.service.calendar;

import com.hyeobjin.application.admin.dto.calendar.AdminFindCalendarDTO;
import com.hyeobjin.application.admin.dto.calendar.UpdateCalendarDTO;
import com.hyeobjin.domain.entity.calendar.Calendar;
import com.hyeobjin.domain.repository.calendar.CalendarJpaRepository;
import lombok.extern.slf4j.Slf4j;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@Slf4j
@SpringBootTest
class AdminCalendarServiceTest {

    @Autowired
    AdminCalendarService adminCalendarService;

    @Autowired
    CalendarJpaRepository calendarJpaRepository;

    @Test
    @DisplayName("관리자 일정 목록 조회")
    void findAllAdmin() {
        List<AdminFindCalendarDTO> result = adminCalendarService.findAll();

        result.stream().forEach(System.out::println);
    }

    @Test
    @DisplayName("관리자 일정 상세 조회")
    void findDetail() {
        Long calendarId = 1L;

        AdminFindCalendarDTO resultDetail = adminCalendarService.findDetail(calendarId);

        System.out.println("resultDetail.getUsers().getName() = " + resultDetail.getUsers().getName());

        Assertions.assertThat(resultDetail.getCalenderId()).isEqualTo(calendarId);
    }

    @Test
    @DisplayName("관리자 일정 업데이트")
    void updateCalendar() {

        UpdateCalendarDTO updateCalendarDTO = new UpdateCalendarDTO();
        updateCalendarDTO.setCalenderId(1L);
        updateCalendarDTO.setTitle("다시 일정 수정");
        updateCalendarDTO.setCalendarYN("Y");
        updateCalendarDTO.setUsersId(1L); // 마지막으로 수정한 관리자 PK

        adminCalendarService.update(updateCalendarDTO);

        AdminFindCalendarDTO findUpdateDetail = adminCalendarService.findDetail(1L);
        Assertions.assertThat(findUpdateDetail.getCalenderId()).isEqualTo(updateCalendarDTO.getCalenderId());
    }

    @Test
    @DisplayName("관리자 일정 삭제")
    void deleteCalendar() {
        Long calendarId = 3L;
        adminCalendarService.delete(calendarId);

        Optional<Calendar> deletedEntity = calendarJpaRepository.findById(calendarId);

        Assertions.assertThat(deletedEntity).isEmpty();
    }
}