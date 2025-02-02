package com.hyeobjin.web.common.calendar.api;

import com.hyeobjin.application.common.dto.calendar.FindCalendarDTO;
import com.hyeobjin.application.common.service.calendar.CalendarService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Slf4j
@RestController
@Tag(name = "COMMON_CALENDAR", description = "메인 페이지에서 전체 일정 조회")
@RequestMapping("/calendar")
@RequiredArgsConstructor
public class CalendarApiController {

    private final CalendarService calendarService;

    /**
     * 메인 페이지에서 모든 일정을 조회하지만 조회 성능을 위해 필요한 데이터만 조회하고 상세페이지에서 해당 pk 를 기준으로
     * 컬럼에 접근
     * 조회 데이터 : calender id, title, startTime, EndTime, CreateAt, findScheduleStatus
     * swagger : O
     *
     * @return 모든일정을 조회한 데이터를 반환
     */
    @Operation(summary = "일정 조회", description = "메인 페이지에서 전체 일정을 조회하기 위한 API 입니다.")
    @GetMapping
    public ResponseEntity<List<FindCalendarDTO>> findAll() {
        return ResponseEntity.ok(calendarService.findAll());
    }
}
