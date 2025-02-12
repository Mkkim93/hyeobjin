package com.hyeobjin.domain.repository.calendar;

import com.hyeobjin.application.admin.dto.calendar.UpdateCalendarDTO;
import com.hyeobjin.domain.entity.calendar.Calendar;
import com.hyeobjin.domain.entity.calendar.enums.ScheduleStatus;
import com.hyeobjin.domain.entity.users.Users;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.querydsl.jpa.impl.JPAUpdateClause;
import jakarta.persistence.EntityManager;
import org.apache.commons.lang3.EnumUtils;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

import static com.hyeobjin.domain.entity.calendar.QCalendar.*;

@Repository
@Transactional
public class CalendarRepositoryImpl extends QuerydslRepositorySupport implements CalendarCustomRepository {

    private final JPAQueryFactory jpaQueryFactory;

    public CalendarRepositoryImpl(EntityManager em) {
        super(Calendar.class);
        jpaQueryFactory = new JPAQueryFactory(em);
    }

    @Override
    public void updateCalendar(UpdateCalendarDTO updateCalendarDTO) {

        EntityManager entityManager = getEntityManager();

        JPAUpdateClause updateClause = new JPAUpdateClause(entityManager, calendar);

        if (updateCalendarDTO.getTitle() != null) {
            updateClause.set(calendar.title, updateCalendarDTO.getTitle());
        }

        if (updateCalendarDTO.getDescription() != null) {
            updateClause.set(calendar.description, updateCalendarDTO.getDescription());
        }

        if (updateCalendarDTO.getStartTime() != null) {
            updateClause.set(calendar.startTime, updateCalendarDTO.getStartTime());
        }

        if (updateCalendarDTO.getEndTime() != null) {
            updateClause.set(calendar.endTime, updateCalendarDTO.getEndTime());
        }

        if (updateCalendarDTO.getLocation() != null) {
            updateClause.set(calendar.location, updateCalendarDTO.getLocation());
        }

        if (updateCalendarDTO.getHoliday() != null) {
            updateClause.set(calendar.holidays, updateCalendarDTO.getHoliday());
        }

        if (updateCalendarDTO.getCalendarYN() != null) {
            updateClause.set(calendar.calenderYN, updateCalendarDTO.getCalendarYN());
        }

        if (updateCalendarDTO.getUsersId() != null) {
            updateClause.set(calendar.users, Users.builder().userId(updateCalendarDTO.getUsersId()).build());
        }

        if (updateCalendarDTO.getScheduleStatus() != null) {
            updateClause.set(calendar.scheduleStatus, EnumUtils.getEnum(ScheduleStatus.class, updateCalendarDTO.getScheduleStatus()));
        }

        updateClause.set(calendar.updateAt, LocalDateTime.now());

        updateClause.where(calendar.id.eq(updateCalendarDTO.getCalendarId()));

        updateClause.execute();
    }
}
