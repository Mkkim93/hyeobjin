package com.hyeobjin.domain.repository.calendar;

import com.hyeobjin.application.admin.dto.calendar.UpdateCalendarDTO;
import com.hyeobjin.domain.entity.calendar.Calendar;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;
import org.springframework.transaction.annotation.Transactional;

import static org.assertj.core.api.Assertions.*;

@Slf4j
@DataJpaTest
@Transactional
@Import(CalendarRepositoryImpl.class)
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@DisplayName("캘린더 (상세 조회)")
class CalendarRepositoryImplTest {

    @Autowired
    private CalendarJpaRepository calendarJpaRepository;

    @Autowired
    private CalendarRepositoryImpl calendarRepositoryImpl;

    @Autowired
    private EntityManager entityManager;

    @Test
    @DisplayName("수정 : 입력한 데이터만 수정 (null 값은 기존 데이터 유지)")
    void updateCalendar() {

        // given
        Long calendarId = 29L;
        String title = "창립기념일";

        // when (조회)
        Calendar findCalendar = calendarJpaRepository.findById(calendarId)
                .orElseThrow(() -> new EntityNotFoundException("해당 일정이 존재하지 않음"));

        // then (수정 전 검증)
        assertThat(findCalendar.getTitle()).isEqualTo(title);

        // given (update)
        UpdateCalendarDTO updateCalendarDTO = new UpdateCalendarDTO();
        updateCalendarDTO.setCalendarId(calendarId);
        updateCalendarDTO.setTitle("창립기념일아님");

        // when (update)
        calendarRepositoryImpl.updateCalendar(updateCalendarDTO);

        entityManager.flush();
        entityManager.clear();

        // then (update)
        Calendar updatedCalendar = calendarJpaRepository.findById(calendarId)
                .orElseThrow(() -> new EntityNotFoundException("수정 후 일정 없음"));

        assertThat(updatedCalendar.getTitle()).isEqualTo(updateCalendarDTO.getTitle());
        assertThat(updatedCalendar.getDescription()).isEqualTo(findCalendar.getDescription());
    }
}