package com.hyeobjin.domain.repository.calendar;

import com.hyeobjin.application.admin.dto.calendar.AdminCalendarSummaryDTO;
import com.hyeobjin.application.common.dto.calendar.FindCalendarDTO;
import com.hyeobjin.domain.entity.calendar.Calendar;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.time.LocalDateTime;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@Slf4j
@DataJpaTest
@DisplayName("캘린터 테스트")
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class CalendarJpaRepositoryTest {

    @Autowired
    private CalendarJpaRepository calendarJpaRepository;

    @Test
    @DisplayName("조회 : 공개된 모든 일정 조회 (calendarYN = Y)")
    void findAllCommon() {

        // when
        List<FindCalendarDTO> result = calendarJpaRepository.findByAllCommon();

        // then
        assertThat(result).isNotEmpty();
        result.stream().forEach(System.out::println);
    }

    @Test
    @DisplayName("조회 : 일정 간단 조회")
    void findAllAdmin() {

        // when
        List<AdminCalendarSummaryDTO> result = calendarJpaRepository.findByAllAdmin();

        // then
        assertThat(result).isNotEmpty();
        result.stream().forEach(System.out::println);
    }

    @Test
    @DisplayName("조회 : 일정 범위 조회 (사용자가 선택한 일정이 포함된 모든 일정 조회)")
    void startTimeBetween() {

        // given
        LocalDateTime start = LocalDateTime.of(2025, 2, 4, 0,0,0);// 오늘 00:00:00

        // when
        List<Calendar> events = calendarJpaRepository.findEventsByDateJPQL(start);

        // then
        events.stream().forEach(System.out::println);
    }
}