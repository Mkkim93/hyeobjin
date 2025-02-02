package com.hyeobjin.domain.repository.calendar;


import com.hyeobjin.application.admin.dto.calendar.UpdateCalendarDTO;


public interface CalendarCustomRepository {

    void updateCalendar(UpdateCalendarDTO updateCalendarDTO);

}
