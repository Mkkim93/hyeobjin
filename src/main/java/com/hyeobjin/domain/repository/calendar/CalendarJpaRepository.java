package com.hyeobjin.domain.repository.calendar;

import com.hyeobjin.application.admin.dto.calendar.AdminCalendarSummaryDTO;
import com.hyeobjin.application.common.dto.calendar.FindCalendarDTO;
import com.hyeobjin.domain.entity.calendar.Calendar;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface CalendarJpaRepository extends JpaRepository<Calendar, Long> {

    @Query("select new com.hyeobjin.application.common.dto.calendar.FindCalendarDTO(c.id, c.title, c.startTime, c.endTime, c.createAt, c.scheduleStatus) " +
            " from Calendar c " +
            " where c.calenderYN = 'Y'")
    List<FindCalendarDTO> findByAllCommon();

    @Query("select new com.hyeobjin.application.admin.dto.calendar.AdminCalendarSummaryDTO(c.id, c.title, c.createAt, c.startTime, c.endTime) " +
            " from Calendar c")
    List<AdminCalendarSummaryDTO> findByAllAdmin();
}
