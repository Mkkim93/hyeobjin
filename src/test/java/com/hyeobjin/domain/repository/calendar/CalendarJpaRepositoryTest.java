package com.hyeobjin.domain.repository.calendar;

import com.hyeobjin.application.admin.dto.calendar.AdminCalendarSummaryDTO;
import com.hyeobjin.application.common.dto.calendar.FindCalendarDTO;
import com.hyeobjin.domain.entity.calendar.Calendar;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@Slf4j
@SpringBootTest
class CalendarJpaRepositoryTest {

    @Autowired
    CalendarJpaRepository calendarJpaRepository;

    @Test
    @DisplayName("일반 사용자 일정 리스트 조회 (calendarYN = Y)")
    void findAllCommon() {
        List<FindCalendarDTO> result = calendarJpaRepository.findByAllCommon();
        result.stream().forEach(System.out::println);
    }

    @Test
    @DisplayName("관리자 일정 리스트 간단 조회")
    void findAllAdmin() {
        List<AdminCalendarSummaryDTO> result = calendarJpaRepository.findByAllAdmin();
        result.stream().forEach(System.out::println);
    }

    @Test
    @DisplayName("일정 범위 조회")
    void startTimeBetween() {
        LocalDateTime start = LocalDateTime.of(2025, 2, 4, 0,0,0);// 오늘 00:00:00
        log.info("start={}", start);
        List<Calendar> events = calendarJpaRepository.findEventsByDateJPQL(start);
        events.stream().forEach(System.out::println);
    }
}