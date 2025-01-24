package com.hyeobjin.web.admin.main.api;

import com.hyeobjin.application.admin.service.users.AdminUsersService;
import com.hyeobjin.application.common.dto.register.RegisterDTO;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequiredArgsConstructor
public class AdminApiController {

    private final AdminUsersService adminUsersService;

    @GetMapping("/admin")
    public String adminP() {
        return "/admin";
    }

    @Operation(summary = "관리자 등록", description = "관리자를 등록하는 API 입니다.")
    @PostMapping("/register")
    public ResponseEntity<String> registerProc(@RequestBody RegisterDTO registerDTO) {

        Boolean isRegistered = adminUsersService.register(registerDTO);

        if (isRegistered) {
            return ResponseEntity.status(HttpStatus.CREATED).body("register front success");
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("register front failed");
        }
    }
}
