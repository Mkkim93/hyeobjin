package com.hyeobjin.web.admin.auth.api;

import com.hyeobjin.application.admin.dto.users.CheckUserDTO;
import com.hyeobjin.application.admin.dto.users.UpdateUserDTO;
import com.hyeobjin.application.admin.service.auth.AdminAuthService;
import com.hyeobjin.jwt.JwtUtil;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

@Slf4j
@Tag(name = "ADMIN_USERS", description = "관리자의 개인 정보를 수정하고 조회하기 위한 API 입니다.")
@RestController
@RequestMapping("/admin/info")
@RequiredArgsConstructor
public class AdminAuthController {

    private final AdminAuthService adminAuthService;

    @PostMapping
    @Operation(summary = "관리자 정보 수정", description = "관리자 정보를 수정하기 위한 API 입니다.")
    public ResponseEntity<String> update(@RequestBody UpdateUserDTO updateUserDTO) {
        adminAuthService.update(updateUserDTO);
        return ResponseEntity.ok("정보 수정이 완료 되었습니다.");
    }

    @PatchMapping("/password")
    @Operation(summary = "관리자 비밀 번호 수정", description = "관리자가 비밀번호를 별도로 수정하기 위한 API 입니다.")
    public ResponseEntity<String> passwordUpdate(@RequestParam("existPassword") String existPassword,
                                                 @RequestParam("updatePassword") String updatePassword) {

        String username = SecurityContextHolder.getContext().getAuthentication().getName();

        boolean exist = adminAuthService.checkUserInfo(username, existPassword);

        if (!exist) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("비밀번호 변경이 실패 하였습니다.");
        }

        adminAuthService.updatePassword(username, updatePassword);
        return ResponseEntity.status(HttpStatus.OK).body("비밀번호 변경이 완료 되었습니다.");
    }

    @PostMapping("/check")
    @Operation(summary = "나의 정보에 접근을 위한 사용자 정보 검증", description = "관리자가 나의 정보 페이지로 접근하기 위해 검증 하는 API 입니다.")
    public ResponseEntity<CheckUserDTO> findMyProfile(@RequestParam("username") String username,
                                                      @RequestParam("password") String password) {
    return ResponseEntity.ok(adminAuthService.findMyProfile(username, password));
    }
}
