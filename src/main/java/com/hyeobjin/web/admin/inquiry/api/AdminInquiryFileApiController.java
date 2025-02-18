package com.hyeobjin.web.admin.inquiry.api;

import com.hyeobjin.application.admin.service.file.AdminInquiryFileService;
import com.hyeobjin.domain.entity.file.FileBox;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriUtils;

import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@Slf4j
@RestController
@Tag(name = "ADMIN_INQUIRY", description = "관리자가 문의 내용에 첨부된 파일을 미리보기 또는 다운로드 하기 위한 API 입니다.")
@RequestMapping("/admin/inquiry/files")
@RequiredArgsConstructor
public class AdminInquiryFileApiController {

    private final AdminInquiryFileService adminInquiryFileService;


    @GetMapping("/download/{fileBoxId}")
    @Operation(summary = "파일 다운로드", description = "fileBoxId 를 조회하여 해당 파일을 다운로드 하는 API 입니다.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "파일 다운로드 성공", content = @Content(mediaType = "application/octet-stream")),
            @ApiResponse(responseCode = "404", description = "파일을 찾을 수 없음"),
            @ApiResponse(responseCode = "500", description = "서버 오류로 인한 파일 다운로드 실패")
    })
    public ResponseEntity<Resource> download(@PathVariable("fileBoxId") Long fileBoxId) throws MalformedURLException {

        FileBox fileBox = adminInquiryFileService.findById(fileBoxId);

        if (fileBox == null) {
            return ResponseEntity.notFound().build();
        }

        String uploadFileName = fileBox.getFileOrgName();
        String fileBoxName = fileBox.getFileName();

        UrlResource urlResource = new UrlResource("file:" + adminInquiryFileService.getFullPath(fileBoxName));

        log.info("urlResource={}", urlResource);
        log.info("fileName={}", uploadFileName);

        String encodeFileName = UriUtils.encode(uploadFileName, StandardCharsets.UTF_8);
        String contentDisposition = "attachment; filename=\"" + encodeFileName + "\"";

        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, contentDisposition)
                .body(urlResource);
    }

    @GetMapping("/preview/{fileBoxId}")
    @Operation(summary = "파일 미리보기", description = "관리자가 문의 내용에 첨부된 파일의 미리보기를 위한 API 입니다.")
    public ResponseEntity<Resource> filePreview(@PathVariable("fileBoxId") Long fileBoxId) throws IOException {

        FileBox fileBox = adminInquiryFileService.findById(fileBoxId);

        if (fileBox == null) {
            return ResponseEntity.notFound().build();
        }

        String fileOrgName = fileBox.getFileOrgName();
        String fileName = fileBox.getFileName();
        String filePath = adminInquiryFileService.getFullPath(fileName);

        Path path = Paths.get(filePath);
        if (!Files.exists(path)) {
            return ResponseEntity.notFound().build();
        }

        String contentType = Files.probeContentType(path);
        if (contentType == null) {
            contentType = "application/octet-stream"; // 기본값
        }

        UrlResource urlResource = new UrlResource("file:" + filePath);

        String encodeFileName = UriUtils.encode(fileOrgName, StandardCharsets.UTF_8);
        String contentDisposition = "attachment; filename=\"" + encodeFileName + "\"";

        return ResponseEntity.ok()
                .contentType(MediaType.parseMediaType(contentType)) // ✅ MIME 타입 설정
                .header(HttpHeaders.CONTENT_DISPOSITION, contentDisposition) // ✅ 다운로드 헤더 설정
                .body(urlResource);
    }
}
