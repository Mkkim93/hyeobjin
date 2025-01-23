package com.hyeobjin.web.admin.item.api;

import com.hyeobjin.application.admin.dto.item.FindAdminItemDTO;
import com.hyeobjin.application.admin.service.item.AdminItemService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Slf4j
@Tag(name = "AdminItem", description = "관리자 제품 관련 API")
@RestController
@RequestMapping("/admin/items")
@RequiredArgsConstructor
public class AdminItemController {

    private final AdminItemService adminItemService;

    @Operation(summary = "관리자 제품 조회" , description = "관리자가 모든 제품을 조건별로 조회하는 페이징 API 입니다.")
    @GetMapping
    public ResponseEntity<?> findItemPageList(@RequestParam(name = "page", defaultValue = "0") int page,
                                              @RequestParam(name = "size", defaultValue = "10") int size,
                                              @RequestParam(name = "manuName", required = false) String manuName) {
        Page<FindAdminItemDTO> adminItemPages = adminItemService.findAdminItemPages(PageRequest.of(page, size), manuName);
        return ResponseEntity.ok(adminItemPages);
    }
}
