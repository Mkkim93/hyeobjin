package com.hyeobjin.web.admin.inquiry.api;

import com.hyeobjin.application.admin.dto.inquiry.FindAdminInquiryDTO;
import com.hyeobjin.application.admin.dto.inquiry.FindAdminInquiryDetailDTO;
import com.hyeobjin.application.admin.service.inquiry.AdminInquiryService;
import com.hyeobjin.domain.entity.file.FileBox;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriUtils;

import java.net.MalformedURLException;
import java.nio.charset.StandardCharsets;
import java.util.List;

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
    @Operation(summary = "관리자 문의 내용 상세 조회", description = "관리자가 문의 내용을 상세 조회하기 위한 API 입니다.")
    public ResponseEntity<FindAdminInquiryDetailDTO> findDetail(@RequestParam(value = "inquiryId") Long inquiryId) {
       return ResponseEntity.ok(adminInquiryService.findDetailWithFiles(inquiryId));
    }

    @GetMapping("/simple")
    @Operation(summary = "관리자 메인 폼에서 최근 문의 조회", description = "관리자가 관리자 메인폼에서 최근 문의 내용을 확인하기 위한 API 입니다.")
    public ResponseEntity<List<FindAdminInquiryDTO>> findSimple() {
        return ResponseEntity.ok(adminInquiryService.findSimple());
    }

    @DeleteMapping
    @Operation(summary = "관리자 문의 내용 삭제", description = "관리자가 특정 문의 글을 삭제하면 해당 파일도 모두 삭제하기 위한 API 입니다.")
    public ResponseEntity<String> delete(@RequestParam("inquiryId") Long inquiryId) {
        adminInquiryService.delete(inquiryId);
        return ResponseEntity.ok("파일 삭제 성공");
    }
}
