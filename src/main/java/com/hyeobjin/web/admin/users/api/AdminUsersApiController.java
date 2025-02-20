package com.hyeobjin.web.admin.users.api;

import com.hyeobjin.application.admin.dto.users.FindUsersDTO;
import com.hyeobjin.application.admin.service.users.AdminUsersService;
import com.hyeobjin.application.common.dto.register.RegisterDTO;
import com.hyeobjin.domain.entity.users.enums.RoleType;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Slf4j
@RestController
@RequestMapping("/admin/users")
@Tag(name = "SUPER_ADMIN_USERS", description = "최종 관리자가 관리자 및 사용자 정보를 확인하고 수정/삭제 하기 위한 API 입니다.")
@RequiredArgsConstructor
public class AdminUsersApiController {

    private final AdminUsersService adminUsersService;

    @PatchMapping("/role")
    @Operation(summary = "관리자 권한 수정", description = "최종 관리자가 관리자 권한을 수정하기 위한 API 입니다.")
    public ResponseEntity<String> updateRoleType(@RequestParam("userId") Long userId, @RequestParam("roleType") String roleType) {

        boolean updateStatus = adminUsersService.updateRoleType(userId, roleType);

        if (!updateStatus) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("권한 변경 실패");
        }
        return ResponseEntity.status(HttpStatus.OK).body("권한 변경 완료");
    }

    @Operation(summary = "관리자 등록", description = "관리자를 등록하는 API 입니다.")
    @PostMapping("/register")
    public ResponseEntity<String> registerProc(@RequestBody RegisterDTO registerDTO) {

        Boolean isRegistered = adminUsersService.register(registerDTO);

        if (isRegistered) {
            return ResponseEntity.status(HttpStatus.CREATED).body("add Admin Register success");
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("add Admin Register failed");
        }
    }

    @GetMapping
    @Operation(summary = "관리자 상세 조회", description = "관리자 목록에서 특정 관리자를 상세 조회 하기 위한 API 입니다.")
    public ResponseEntity<FindUsersDTO> findDetail(@RequestParam("usersId") Long usersId) {
        return ResponseEntity.ok(adminUsersService.detail(usersId));
    }



    @DeleteMapping
    @Operation(summary = "관리자 정보 삭제", description = "관리자 정보를 모두 삭제하기 위한 API 입니다.")
    public ResponseEntity<String> delete(@RequestParam("usersId") Long usersId) {
        adminUsersService.delete(usersId);
        return ResponseEntity.ok("관리자 정보가 삭제 되었습니다.");
    }

    @GetMapping("/roles")
    public ResponseEntity<List<Map<String, String>>> getRoleTypes() {
        List<Map<String, String>> roles = Arrays.stream(RoleType.values())
                .map(role -> Map.of(
                        "name", role.name(),
                        "description", role.getDescription()
                ))
                .collect(Collectors.toList());

        return ResponseEntity.ok(roles);
    }

    @Operation(summary = "관리자 조회", description = "현재 모든 관리자 정보를 조회하는 API 입니다.")
    @GetMapping("/all")
    public ResponseEntity<Page<FindUsersDTO>> findAll(@RequestParam(name = "page", defaultValue = "0") int page,
                                                      @RequestParam(name = "size", defaultValue = "5") int size) {
        Page<FindUsersDTO> findAllAdmin = adminUsersService.findAll(PageRequest.of(page, size));
        return ResponseEntity.ok(findAllAdmin);
    }
}
