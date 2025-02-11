package com.hyeobjin.application.admin.dto.calendar;

import com.hyeobjin.domain.entity.calendar.Calendar;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 상세 조회를 위한 DTO
 */
@Data
@NoArgsConstructor
public class AdminFindCalendarDTO {

    private Long calenderId;
    private String title;
    private String description;
    private LocalDateTime startTime;
    private LocalDateTime endTime;
    private LocalDateTime createAt;
    private LocalDateTime updateAt;
    private String location;
    private String calendarYN;
    private Long usersId;
    private String writer;

    public AdminFindCalendarDTO(Long calenderId, String title, LocalDateTime endTime, LocalDateTime startTime, String description) {
        this.calenderId = calenderId;
        this.title = title;
        this.endTime = endTime;
        this.startTime = startTime;
        this.description = description;
    }

    public List<AdminFindCalendarDTO> toDto(List<Calendar> calendars) {
        return calendars.stream()
                .map(this::convertToDto) // ✅ 개별 변환 메서드 호출
                .collect(Collectors.toList());
    }

    private AdminFindCalendarDTO convertToDto(Calendar calendar) {
        return new AdminFindCalendarDTO(
                calendar.getId(),
                calendar.getTitle(),
                calendar.getStartTime(),
                calendar.getEndTime(),
                calendar.getDescription()
        );
    }

}
