package com.hyeobjin.domain.repository.calendar;

import com.hyeobjin.application.admin.dto.calendar.AdminCalendarSummaryDTO;
import com.hyeobjin.application.admin.dto.calendar.AdminFindCalendarDTO;
import lombok.extern.slf4j.Slf4j;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;


import java.util.List;

import static org.assertj.core.api.Assertions.*;

@Slf4j
@DataJpaTest
class CalendarRepositoryImplTest {

    @Autowired
    CalendarJpaRepository calendarJpaRepository;

    @Test
    @DisplayName("관리자 전체 일정 목록 조회")
    void findAllCalender() {
        List<AdminCalendarSummaryDTO> result = calendarJpaRepository.findByAllAdmin();
        Assertions.assertThat(result).isNotNull();
        result.stream().forEach(System.out::println);
    }
}