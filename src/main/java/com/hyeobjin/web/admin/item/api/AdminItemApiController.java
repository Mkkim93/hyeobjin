package com.hyeobjin.web.admin.item.api;

import com.hyeobjin.application.admin.dto.item.FindAdminItemDTO;
import com.hyeobjin.application.admin.service.item.AdminItemService;
import com.hyeobjin.application.common.dto.item.FindByItemDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@Tag(name = "AdminItem", description = "관리자 제품 관련 API")
@RestController
@RequestMapping("/admin/items")
@RequiredArgsConstructor
public class AdminItemApiController {

    private final AdminItemService adminItemService;

    @Operation(summary = "관리자 제품 조회" , description = "관리자가 모든 제품을 조건별로 조회하는 페이징 API 입니다.")
    @GetMapping
    public ResponseEntity<?> findItemPageList(@RequestParam(name = "page", defaultValue = "0") int page,
                                              @RequestParam(name = "size", defaultValue = "10") int size,
                                              @RequestParam(name = "manuName", required = false) String manuName
                                              ) {
        PageRequest pageRequest = PageRequest.of(page, size);

        Page<FindAdminItemDTO> adminItemPages = adminItemService.findAdminItemPages(pageRequest, manuName);

        return ResponseEntity.ok(adminItemPages);
    }

    @Operation(summary = "관리자 제품 상세 조회", description = "관리자가 제품을 상세 조회하기 위한 API 입니다.")
    @GetMapping("/detail")
    public ResponseEntity<FindByItemDTO> findItemDetail(@RequestParam("manuId") Long manuId,
                                                        @RequestParam("itemId") Long itemId
                                                        ) {
        FindByItemDTO findAdminItemDTO = new FindByItemDTO();
        findAdminItemDTO.setItemId(itemId);
        findAdminItemDTO.setManuId(manuId);
        return ResponseEntity.ok(adminItemService.findByItemDetail(findAdminItemDTO));
    }

    @Operation(summary = "관리자 제품 상세 조회 (수정)", description = "관리자가 제품 pk 를 기준으로 상세조회 하기 위한 API 입니다.")
    @GetMapping("/modify")
    public ResponseEntity<?> findItemModify(@RequestParam("itemId") Long itemId) {

        FindByItemDTO findByItemDTO = new FindByItemDTO();
        findByItemDTO.setItemId(itemId);
        
        return ResponseEntity.ok(adminItemService.findByItemId(itemId));

    }

}
