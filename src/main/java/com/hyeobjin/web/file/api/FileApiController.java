package com.hyeobjin.web.file.api;

import com.hyeobjin.application.service.file.FileBoxService;
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
@Tag(name = "ItemFileBox", description = "제품 파일 관련 API")
@RestController
@RequestMapping("/files")
@RequiredArgsConstructor
public class FileApiController {

    private final FileBoxService fileBoxService;

    /**
     * 기존 제품의 파일 데이터 추가
     * postman api : O
     */
    @Operation(summary = "파일 등록", description = "제품의 itemId 값을 기준으로 파일을 등록하는 API 입니다.")
    @PostMapping
    public ResponseEntity<String> addFile(@RequestParam("itemId") Long itemId,
                                          @RequestPart("files") List<MultipartFile> files) throws IOException {

        fileBoxService.saveFileOnly(itemId, files);

        return ResponseEntity.ok("item file save success");
    }

    /**
     * 현재 등록중이거나 등록된 파일의 데이터 삭제 (정적파일 & 메타데이터)
     * postman api : O
     * @param fileBoxId 삭제할 file 의 기본키
     * @return
     */
    @Operation(summary = "파일 삭제", description = "fileBoxId 값을 기준으로 파일을 삭제 API 입니다. (메타데이터 & 정적파일 모두 삭제)")
    @DeleteMapping
    public ResponseEntity<String> deleteFile(@RequestParam("fileBoxId") Long fileBoxId) {

        fileBoxService.deleteFile(fileBoxId);

        return ResponseEntity.ok("파일 삭제 성공");
    }

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
        FileBox fileBox = fileBoxService.findById(fileBoxId);

        if (fileBox == null) {
            return ResponseEntity.notFound().build(); // 파일이 없을 경우 404 에러
        }

        String uploadFileName = fileBox.getFileOrgName();
        String fileBoxName = fileBox.getFileName();

        UrlResource urlResource = new UrlResource("file:" + fileBoxService.getFullPath(fileBoxName));

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
