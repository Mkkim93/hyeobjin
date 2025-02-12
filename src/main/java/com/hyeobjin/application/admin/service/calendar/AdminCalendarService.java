package com.hyeobjin.application.admin.service.calendar;

import com.hyeobjin.application.admin.dto.calendar.AdminCalendarSummaryDTO;
import com.hyeobjin.application.admin.dto.calendar.AdminFindCalendarDTO;
import com.hyeobjin.application.admin.dto.calendar.CreateCalendarDTO;
import com.hyeobjin.application.admin.dto.calendar.UpdateCalendarDTO;
import com.hyeobjin.domain.entity.calendar.Calendar;
import com.hyeobjin.domain.repository.calendar.CalendarJpaRepository;
import com.hyeobjin.domain.repository.calendar.CalendarRepositoryImpl;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class AdminCalendarService {

    private final CalendarRepositoryImpl calendarRepositoryImpl;
    private final CalendarJpaRepository calendarJpaRepository;

    /**
     * 관리자 일정 등록
     */
    public void save(CreateCalendarDTO createCalendarDTO) {

        Calendar saveEntity = createCalendarDTO.saveToEntity(createCalendarDTO);

        calendarJpaRepository.save(saveEntity);
    }

    /**
     * 관리자 일정 조회
     */
    public List<AdminCalendarSummaryDTO> findAll() {

       return calendarJpaRepository.findByAllAdmin();

    }

    public List<AdminFindCalendarDTO> findDetail(LocalDateTime startTime) {

        List<Calendar> result = calendarJpaRepository.findEventsByDateJPQL(startTime);

        return new AdminFindCalendarDTO().toDto(result);
    }

    /**
     * 관리자 일정 수정
     */
    public void update(UpdateCalendarDTO updateCalendarDTO) {

        calendarJpaRepository.findById(
                updateCalendarDTO.getCalendarId())
                .orElseThrow(() -> new EntityNotFoundException("일정이 존재 하지 않습니다."));

        calendarRepositoryImpl.updateCalendar(updateCalendarDTO);

    }

    /**
     * 관리자 일정 삭제
     */
    public void delete(Long calendarId) {

        Calendar deleteEntity = calendarJpaRepository.findById(calendarId)
                .orElseThrow(() -> new EntityNotFoundException("일정이 존재 하지 않습니다."));

        calendarJpaRepository.delete(deleteEntity);
    }
}
