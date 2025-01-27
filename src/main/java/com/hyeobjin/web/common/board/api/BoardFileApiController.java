package com.hyeobjin.web.common.board.api;

import com.hyeobjin.application.common.service.board.BoardFileService;
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
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.util.UriUtils;

import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.charset.StandardCharsets;
import java.util.List;

@Slf4j
@Tag(name = "BoardFileBox", description = "게시판 파일 관련 API")
@RestController
@RequestMapping("/boardFiles")
@RequiredArgsConstructor
public class BoardFileApiController {

    private final BoardFileService boardFileService;

    @Operation(summary = "게시글 파일 다운로드", description = "게시글의 파일을 다운로드하기 위한 API 입니다.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "파일 다운로드 성공", content = @Content(mediaType = "application/octet-stream")),
            @ApiResponse(responseCode = "404", description = "파일을 찾을 수 없음"),
            @ApiResponse(responseCode = "500", description = "서버 오류로 인한 파일 다운로드 실패")
    })
    @PostMapping("/download/{fileBoxId}")
    public ResponseEntity<Resource> download(@PathVariable("fileBoxId") Long fileBoxId) throws MalformedURLException {

        FileBox fileBox = boardFileService.findById(fileBoxId);

        if (fileBox == null) {
            return ResponseEntity.notFound().build();
        }

        String uploadFileName = fileBox.getFileOrgName();
        String fileBoxName = fileBox.getFileName();

        UrlResource urlResource = new UrlResource("file:" + boardFileService.getFullPath(fileBoxName));

        log.info("urlResource={}", urlResource);
        log.info("fileName={}", uploadFileName);

        String encodeFileName = UriUtils.encode(uploadFileName, StandardCharsets.UTF_8);
        String contentDisposition = "attachment; filename=\"" + encodeFileName + "\"";

        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, contentDisposition)
                .body(urlResource);
    }
}
