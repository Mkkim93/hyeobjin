package com.hyeobjin.web.admin.board.api;

import com.hyeobjin.application.common.service.board.BoardFileService;
import com.hyeobjin.domain.entity.file.FileBox;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.util.UriUtils;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

@Slf4j
@Tag(name = "ADMIN_BOARD_FILE", description = "관리자 권한으로 게시판의 파일 데이터를 관리하기 위한 REST API 입니다.")
@RestController
@RequestMapping("/admin/boardfiles")
@RequiredArgsConstructor
public class AdminFileBoardApiController {

    private final BoardFileService boardFileService;

    /**
     * 파일데이터만 추가됩니다
     * 기존 파일 삭제 : deleteFile()
     * # postman api : O
     */
    @Operation(summary = "게시글 파일 추가", description = "게시글 상세 페이지에서 파일 게시물을 추가하는 API 입니다.")
    @PostMapping
    public ResponseEntity<String> addFile(@RequestParam("boardId") Long boardId,
                                          @RequestPart("files") List<MultipartFile> files) throws IOException {
        boardFileService.saveFileOnly(boardId, files);

        return ResponseEntity.ok("board file save success");
    }

    /**
     * 파일 데이터를 삭제하는 메타데이터 & 정적파일 모두 삭제
     * postman api : O
     */
    // TODO 게시글 상세 페이지에서 자신이 작성한 파일을 삭제
    @Operation(summary = "게시글 파일 삭제", description = "게시글 상세 페이지에서 해당 파일 데이터를 삭제하는 API 입니다.")
    @DeleteMapping
    public ResponseEntity<String> deleteFile(@RequestParam("boardId") Long boardId,
                                             @RequestParam("fileBoxId") Long fileBoxId) {
        boardFileService.delete(fileBoxId, boardId);
        return ResponseEntity.ok("게시글 내의 파일 삭제 성공");
    }

    @Operation(summary = "게시글 수정 중 파일 삭제", description = "게시글 상세 페이지에서 파일의 메타 데이터 및 정적 파일을 삭제하는 API 입니다.")
    @DeleteMapping("/delete")
    public ResponseEntity<?> staticFileDelete(@RequestParam("fileName") String fileName) {
        log.info("fileName={}", fileName);
        boardFileService.staticFileDelete(fileName);

        return ResponseEntity.ok("파일 삭제 성공");
    }

    @Operation(summary = "게시판 파일 다운로드", description = "관리자 게시판 파일 다운로드를 위한 API 입니다.")
    @GetMapping("/download/{fileBoxId}")
    public ResponseEntity<Resource> downLoadFile(@PathVariable("fileBoxId") Long fileBoxId) throws IOException {

        FileBox fileBox = boardFileService.findById(fileBoxId);
        if (fileBox == null) {
            return ResponseEntity.notFound().build();
        }

        String fileOrgName = fileBox.getFileOrgName();
        String fileName = fileBox.getFileName();
        String filePath = boardFileService.getFullPath(fileName);

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

    @GetMapping("/preview/{fileBoxId}")
    @Operation(summary = "파일 미리보기", description = "관리자 게시글 파일 미리보기를 위한 API 입니다.")
    public ResponseEntity<Resource> filePreview(@PathVariable("fileBoxId") Long fileBoxId) throws IOException {

        FileBox fileBox = boardFileService.findById(fileBoxId);

        if (fileBox == null) {
            return ResponseEntity.notFound().build();
        }

        String fileOrgName = fileBox.getFileOrgName();
        String fileName = fileBox.getFileName();
        String filePath = boardFileService.getFullPath(fileName);

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
