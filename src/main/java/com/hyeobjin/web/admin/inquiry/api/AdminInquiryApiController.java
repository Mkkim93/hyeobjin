package com.hyeobjin.web.admin.inquiry.api;


import com.hyeobjin.application.admin.dto.inquiry.FindAdminInquiryDTO;
import com.hyeobjin.application.admin.dto.inquiry.FindAdminInquiryDetailDTO;
import com.hyeobjin.application.admin.service.inquiry.AdminInquiryService;
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
@Tag(name = "ADMIN_INQUIRY", description = "관리자가 1:1 문의 내용을 조회하기 위한 API")
@RestController
@RequestMapping("/admin/inquiry")
@RequiredArgsConstructor
public class AdminInquiryApiController {

    private final AdminInquiryService adminInquiryService;

    @GetMapping
    @Operation(summary = "관리자 전체 문의 리스트 조회", description = "관리자가 관리자 폼에서 문의 목록을 조회하기 위한 API 입니다.")
    public ResponseEntity<Page<FindAdminInquiryDTO>> findAll(@RequestParam(value = "page", defaultValue = "0", required = false) int page,
                                                             @RequestParam(value = "size", defaultValue = "10", required = false) int size) {

        PageRequest pageRequest = PageRequest.of(page, size);

        return ResponseEntity.ok(adminInquiryService.findAll(pageRequest));
    }

    @GetMapping("/detail")
    public ResponseEntity<FindAdminInquiryDetailDTO> findDetail(@RequestParam(value = "inquiryId") Long inquiryId) {
       return ResponseEntity.ok(adminInquiryService.findDetailWithFiles(inquiryId));
    }
}
