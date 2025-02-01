package com.hyeobjin.web.common.file.api;

import com.hyeobjin.application.admin.service.file.AdminItemFileService;
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
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriUtils;

import java.net.MalformedURLException;
import java.nio.charset.StandardCharsets;

@Slf4j
@Tag(name = "COMMON_FILE", description = "제품 파일 관련 API")
@RestController
@RequestMapping("/files")
@RequiredArgsConstructor
public class FileApiController {

    private final AdminItemFileService adminItemFileService;

    /**
     * 파일 데이터 다운로드를 위한 컨트롤러입니다.
     * swagger : O
     * @param fileBoxId id 를 조회하여 해당 파일을 다운로드 합니다.
     * @return
     * @throws MalformedURLException
     */
    @Operation(summary = "파일 다운로드", description = "fileBoxId 를 조회하여 해당 파일을 다운로드 하는 API 입니다.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "파일 다운로드 성공", content = @Content(mediaType = "application/octet-stream")),
            @ApiResponse(responseCode = "404", description = "파일을 찾을 수 없음"),
            @ApiResponse(responseCode = "500", description = "서버 오류로 인한 파일 다운로드 실패")
    })
    @PostMapping("/download/{fileBoxId}")
    public ResponseEntity<Resource> download(@PathVariable("fileBoxId") Long fileBoxId) throws MalformedURLException {

        FileBox fileBox = adminItemFileService.findById(fileBoxId);

        if (fileBox == null) {
            return ResponseEntity.notFound().build(); // 파일이 없을 경우 404 에러
        }

        String uploadFileName = fileBox.getFileOrgName();
        String fileBoxName = fileBox.getFileName();

        UrlResource urlResource = new UrlResource("file:" + adminItemFileService.getFullPath(fileBoxName));

        log.info("urlResource={}", urlResource);
        log.info("fileName={}", uploadFileName);

        String encodeFileName = UriUtils.encode(uploadFileName, StandardCharsets.UTF_8);
        String contentDisposition = "attachment; filename=\"" + encodeFileName + "\"";

        // 파일 응답
        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, contentDisposition)
                .body(urlResource);
    }
}
