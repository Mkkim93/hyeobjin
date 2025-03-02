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
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Slf4j
@Transactional
@DisplayName("관리자 캘린더 테스트")
@SpringBootTest
class AdminCalendarServiceTest {

    @Autowired
    private AdminCalendarService adminCalendarService;

    @Autowired
    private CalendarJpaRepository calendarJpaRepository;

    @Test
    @DisplayName("관리자 일정 상세 조회")
    void findDetail() {

        // given
        Long calendarId = 1L;

        // when
        Calendar calendar = calendarJpaRepository.findById(calendarId).get();

        // then
        List<AdminFindCalendarDTO> result = adminCalendarService.findDetail(calendar.getStartTime());
        result.stream().forEach(System.out::println);
    }

    @Test
    @DisplayName("관리자 일정 업데이트")
    void updateCalendar() {

        // given
        UpdateCalendarDTO updateCalendarDTO = new UpdateCalendarDTO();
        updateCalendarDTO.setCalendarId(1L);
        updateCalendarDTO.setTitle("다시 일정 수정");
        updateCalendarDTO.setCalendarYN("Y");
        updateCalendarDTO.setUsersId(1L); // 마지막으로 수정한 관리자 PK

        // when
        adminCalendarService.update(updateCalendarDTO);

        // then
        List<AdminFindCalendarDTO> detail = adminCalendarService.findDetail(updateCalendarDTO.getStartTime());
        System.out.println(detail);
    }


    @Test
    @DisplayName("관리자 일정 삭제")
    void deleteCalendar() {

        // given
        Long calendarId = 3L;

        // when
        adminCalendarService.delete(calendarId);

        // then
        Optional<Calendar> deletedEntity = calendarJpaRepository.findById(calendarId);
        Assertions.assertThat(deletedEntity).isEmpty();
    }
}