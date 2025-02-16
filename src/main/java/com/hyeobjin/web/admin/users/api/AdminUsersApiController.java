package com.hyeobjin.web.admin.users.api;

import com.hyeobjin.application.admin.dto.users.FindUsersDTO;
import com.hyeobjin.application.admin.dto.users.UpdateUserDTO;
import com.hyeobjin.application.admin.service.users.AdminUsersService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.annotation.RequestScope;

@Slf4j
@RestController
@RequestMapping("/admin/users")
@Tag(name = "ADMIN_USERS", description = "관리자의 정보를 확인하고 수정/삭제 하기 위한 API 입니다.")
@RequiredArgsConstructor
public class AdminUsersApiController {

    private final AdminUsersService adminUsersService;

    @GetMapping
    @Operation(summary = "관리자 상세 조회", description = "관리자 목록에서 특정 관리자를 상세 조회 하기 위한 API 입니다.")
    public ResponseEntity<FindUsersDTO> findDetail(@RequestParam("usersId") Long usersId) {
        return ResponseEntity.ok(adminUsersService.detail(usersId));
    }

    @PostMapping
    @Operation(summary = "관리자 정보 수정", description = "관리자 정보를 수정하기 위한 API 입니다.")
    public ResponseEntity<String> update(@ModelAttribute("updateUsersDTO") UpdateUserDTO updateUserDTO) {
        adminUsersService.update(updateUserDTO);
        return ResponseEntity.ok("정보 수정이 완료 되었습니다.");
    }

    @DeleteMapping
    @Operation(summary = "관리자 정보 삭제", description = "관리자 정보를 모두 삭제하기 위한 API 입니다.")
    public ResponseEntity<String> delete(@RequestParam("usersId") Long usersId) {
        adminUsersService.delete(usersId);
        return ResponseEntity.ok("관리자 정보가 삭제 되었습니다.");
    }
}
