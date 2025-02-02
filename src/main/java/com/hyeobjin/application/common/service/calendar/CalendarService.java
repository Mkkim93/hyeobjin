package com.hyeobjin.application.common.service.calendar;

import com.hyeobjin.application.common.dto.calendar.FindCalendarDTO;
import com.hyeobjin.domain.repository.calendar.CalendarJpaRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class CalendarService {

    private final CalendarJpaRepository calendarJpaRepository;

    /**
     * 모든 일정 조회
     * @return
     */
    public List<FindCalendarDTO> findAll() {
        return calendarJpaRepository.findByAllCommon();
    }
}
