package com.hyeobjin.domain.entity.calendar;

import com.hyeobjin.application.admin.dto.calendar.UpdateCalendarDTO;
import com.hyeobjin.domain.entity.calendar.enums.ScheduleStatus;
import com.hyeobjin.domain.entity.users.Users;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.apache.commons.lang3.EnumUtils;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

@Entity
@Getter
@Table(name = "calendar")
@EntityListeners(AuditingEntityListener.class)
@NoArgsConstructor
public class Calendar {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "title")
    private String title;

    @Column(name = "description")
    private String description;

    @Column(name = "start_time")
    private LocalDateTime startTime;

    @Column(name = "end_time")
    private LocalDateTime endTime;

    @CreatedDate
    @Column(name = "create_at", updatable = false)
    private LocalDateTime createAt;

    @LastModifiedDate
    @Column(name = "updated_at")
    private LocalDateTime updateAt;

    @Column(name = "location")
    private String location;

    @Enumerated(EnumType.STRING)
    @Column(name = "schedule_status")
    private ScheduleStatus scheduleStatus; // enums (PLANNED, ONGOING, COMPLETED, CANCELED)

    @Column(name = "calendar_YN")
    private String calenderYN;

    @Column(name = "holidays")
    private String holidays; // 휴무일 CRUD 구현

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "users_id")
    private Users users;

    @Builder
    public Calendar(String title, String description, LocalDateTime startTime, LocalDateTime endTime,
                     String location, ScheduleStatus scheduleStatus, String calenderYN, Long usersId) {
        this.title = title;
        this.description = description;
        this.startTime = startTime;
        this.endTime = endTime;
        this.location = location;
        this.scheduleStatus = scheduleStatus;
        this.calenderYN = calenderYN;
        this.users = Users.builder()
                .userId(usersId)
                .build();
    }

    public void update(UpdateCalendarDTO updateCalendarDTO) {
        this.title = updateCalendarDTO.getTitle();
        this.description = updateCalendarDTO.getDescription();
        this.createAt = updateCalendarDTO.getCreateAt();
        this.startTime = updateCalendarDTO.getStartTime();
        this.endTime = updateCalendarDTO.getEndTime();
        this.location = updateCalendarDTO.getLocation();
        this.scheduleStatus = EnumUtils.getEnum(ScheduleStatus.class, updateCalendarDTO.getScheduleStatus());
        this.calenderYN = updateCalendarDTO.getCalendarYN();
        this.users = Users.builder()
                .userId(updateCalendarDTO.getUsersId())
                .build();
    }

    @Override
    public String toString() {
        return "Calendar{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", description='" + description + '\'' +
                ", startTime=" + startTime +
                ", endTime=" + endTime +
                ", createAt=" + createAt +
                ", updateAt=" + updateAt +
                ", location='" + location + '\'' +
                ", scheduleStatus=" + scheduleStatus +
                ", calenderYN='" + calenderYN + '\'' +
                ", holidays='" + holidays + '\'' +
                ", users=" + users +
                '}';
    }
}
