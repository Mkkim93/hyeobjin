package com.hyeobjin.domain.repository.calendar;

import com.hyeobjin.application.admin.dto.calendar.AdminFindCalendarDTO;
import com.hyeobjin.application.common.dto.calendar.FindCalendarDTO;
import com.hyeobjin.domain.entity.calendar.Calendar;
import lombok.extern.slf4j.Slf4j;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.origin.SystemEnvironmentOrigin;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.cglib.core.Local;

import java.time.LocalDate;
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
//        List<AdminFindCalendarDTO> result = calendarJpaRepository.findByAllAdmin();
//
//        result.stream().forEach(System.out::println);
    }

    @Test
    @DisplayName("일정 범위 조회")
    void startTimeBetween() {

        LocalDate today = LocalDate.now();
        LocalDateTime startOfDay = today.atStartOfDay(); // 오늘 00:00:00
        LocalDateTime endOfDay = today.atTime(23, 59, 59); // 오늘 23:59:59

        List<Calendar> events = calendarJpaRepository.findByStartTimeBetween(startOfDay, endOfDay);

        System.out.println(events.get(0));

    }
}