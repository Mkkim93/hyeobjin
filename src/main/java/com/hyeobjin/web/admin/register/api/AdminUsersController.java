package com.hyeobjin.web.admin.register.api;

import com.hyeobjin.application.admin.dto.users.FindUsersDTO;
import com.hyeobjin.application.admin.service.users.AdminUsersService;
import com.hyeobjin.application.dto.register.RegisterDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@Tag(name = "AdminUsers", description = "관리자 등록 관련 API")
@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class AdminUsersController {

    private final AdminUsersService adminUsersService;

    @Operation(summary = "관리자 조회", description = "현재 모든 관리자 정보를 조회하는 API 입니다.")
    @GetMapping
    public ResponseEntity<Page<FindUsersDTO>> findAll(@RequestParam(name = "page", defaultValue = "0") int page,
                                                      @RequestParam(name = "size", defaultValue = "5") int size) {
        Page<FindUsersDTO> findAllAdmin = adminUsersService.findAll(PageRequest.of(page, size));
        return ResponseEntity.ok(findAllAdmin);
    }
}
