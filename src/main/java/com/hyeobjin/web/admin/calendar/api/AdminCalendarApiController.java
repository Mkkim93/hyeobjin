package com.hyeobjin.web.admin.calendar.api;

import com.hyeobjin.application.admin.dto.calendar.AdminCalendarSummaryDTO;
import com.hyeobjin.application.admin.dto.calendar.AdminFindCalendarDTO;
import com.hyeobjin.application.admin.dto.calendar.CreateCalendarDTO;
import com.hyeobjin.application.admin.dto.calendar.UpdateCalendarDTO;
import com.hyeobjin.application.admin.service.calendar.AdminCalendarService;
import com.hyeobjin.application.admin.service.calendar.CalendarAuthService;
import com.hyeobjin.jwt.JwtUtil;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@Slf4j
@Tag(name = "ADMIN_CALENDAR", description = "관리자 일정 관련 API")
@RestController
@RequestMapping("/admin/calendar")
@RequiredArgsConstructor
public class AdminCalendarApiController {

    private final JwtUtil jwtUtil;
    private final AdminCalendarService adminCalendarService;
    private final CalendarAuthService calendarAuthService;

    /**
     * swagger : O
     * 관리자가 모든 일정을 조회하기 위한 API
     * @return
     */
    @Operation(summary = "관리자 일정 목록 조회", description = "관리자가 모든 일정을 조회하기 위한 API 입니다.")
    @GetMapping
    public ResponseEntity<List<AdminCalendarSummaryDTO>> findAll() {
        return ResponseEntity.ok(adminCalendarService.findAll());
    }

    /**
     * swagger : O
     * 관리자가 해당 CALENDER PK 를 기준으로 일정 상제 조회
     * @param
     * @return
     */
    @Operation(summary = "관리자 일정 상세 조회", description = "관리자가 상세 일정을 조회하기 위한 API 입니다.")
    @GetMapping("/detail")
    public ResponseEntity<List<AdminFindCalendarDTO>> findDetail(
            @Parameter(description = "조회 시작 시간 (예: 2025-02-11T00:00:00)", example = "2025-02-11T00:00:00")
            @RequestParam("startTime") @Schema(type = "string", format = "date-time") LocalDateTime startTime
    ) {
        return ResponseEntity.ok(adminCalendarService.findDetail(startTime));
    }

    /**
     * 관리자 일정 등록
     * swagger : O
     * @param createCalenderDTO 일정 등록을 위한 DTO
     * @return
     */
    @Operation(summary = "관리자 일정 등록", description = "관리자가 일정을 등록하기 위한 API 입니다.")
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<String> save(@RequestBody CreateCalendarDTO createCalenderDTO) {

        String name = SecurityContextHolder.getContext().getAuthentication().getName();

        Long userId = calendarAuthService.findUserId(name);

        createCalenderDTO.setUsersId(userId);

        adminCalendarService.save(createCalenderDTO);

        return ResponseEntity.ok("Calendar event successfully created.");
    }

    /**
     * swagger : O
     * @param updateCalendarDTO 일정을 수정할 DTO 객체
     * @return
     */
    @Operation(summary = "관리자 일정 수정", description = "관리자가 일정을 수정하기 위한 API 입니다.")
    @PostMapping("/update")
    public ResponseEntity<?> update(@RequestBody UpdateCalendarDTO updateCalendarDTO) {

        adminCalendarService.update(updateCalendarDTO);

        return ResponseEntity.ok("update success");
    }

    /**
     * swagger : O
     * @param calendarId 에 해당하는 일정 삭제
     * @return
     */
    @Operation(summary = "관리자 일정 삭제", description = "관리자가 Calendar PK 를 기준으로 일정을 삭제하기 위한 API 입니다.")
    @DeleteMapping
    public ResponseEntity<String> delete(@RequestParam("calendarId") Long calendarId) {
        adminCalendarService.delete(calendarId);
        return ResponseEntity.ok("일정이 삭제 되었습니다.");
    }
}
