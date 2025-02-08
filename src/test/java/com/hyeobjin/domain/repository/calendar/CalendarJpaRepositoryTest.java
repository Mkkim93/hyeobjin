package com.hyeobjin.domain.repository.calendar;

import com.hyeobjin.application.admin.dto.calendar.AdminFindCalendarDTO;
import com.hyeobjin.application.common.dto.calendar.FindCalendarDTO;
import lombok.extern.slf4j.Slf4j;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

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
}