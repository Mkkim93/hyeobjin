package com.hyeobjin.domain.repository.calendar;

import com.hyeobjin.application.admin.dto.calendar.AdminCalendarSummaryDTO;
import com.hyeobjin.application.common.dto.calendar.FindCalendarDTO;
import com.hyeobjin.domain.entity.calendar.Calendar;
import io.lettuce.core.dynamic.annotation.Param;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDateTime;
import java.util.List;

public interface CalendarJpaRepository extends JpaRepository<Calendar, Long> {

    @Query("select new com.hyeobjin.application.common.dto.calendar.FindCalendarDTO(c.id, c.title, c.startTime, c.endTime, c.createAt) " +
            " from Calendar c " +
            " where c.calenderYN = 'Y'")
    List<FindCalendarDTO> findByAllCommon();

    @Query("select new com.hyeobjin.application.admin.dto.calendar.AdminCalendarSummaryDTO(c.id, c.title, c.createAt, c.startTime, c.endTime) " +
            " from Calendar c")
    List<AdminCalendarSummaryDTO> findByAllAdmin();

    @Query("select c from Calendar c where function('DATE', c.startTime) <= :date and function('DATE', c.endTime) >= :date")
    List<Calendar> findEventsByDateJPQL(@Param("date") LocalDateTime date);
}
