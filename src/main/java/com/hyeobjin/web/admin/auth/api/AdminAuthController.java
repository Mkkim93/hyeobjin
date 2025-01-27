package com.hyeobjin.web.admin.auth.api;

import com.hyeobjin.application.admin.dto.users.FindUsersDTO;
import com.hyeobjin.application.admin.service.auth.AdminAuthService;
import com.hyeobjin.application.common.dto.register.RegisterDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@Tag(name = "ADMIN_USERS", description = "관리자가 권한 부여 및 커스텀을 위한 REST API 입니다.")
@RestController
@RequiredArgsConstructor
public class AdminAuthController {

    private final AdminAuthService adminAuthService;

    @Operation(summary = "관리자 조회", description = "현재 모든 관리자 정보를 조회하는 API 입니다.")
    @GetMapping("/users")
    public ResponseEntity<Page<FindUsersDTO>> findAll(@RequestParam(name = "page", defaultValue = "0") int page,
                                                      @RequestParam(name = "size", defaultValue = "5") int size) {
        Page<FindUsersDTO> findAllAdmin = adminAuthService.findAll(PageRequest.of(page, size));
        return ResponseEntity.ok(findAllAdmin);
    }

    @Operation(summary = "관리자 등록", description = "관리자를 등록하는 API 입니다.")
    @PostMapping("/register")
    public ResponseEntity<String> registerProc(@RequestBody RegisterDTO registerDTO) {

        Boolean isRegistered = adminAuthService.register(registerDTO);

        if (isRegistered) {
            return ResponseEntity.status(HttpStatus.CREATED).body("register front success");
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("register front failed");
        }
    }
}
