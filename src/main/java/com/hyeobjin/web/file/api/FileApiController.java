package com.hyeobjin.web.file.api;

import com.hyeobjin.application.service.file.FileBoxService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@Tag(name = "FileBox", description = "파일 관련 API")
@RestController
@RequestMapping("/files")
@RequiredArgsConstructor
public class FileApiController {

    private final FileBoxService fileBoxService;

    /**
     * 현재 등록중이거나 등록된 파일의 데이터 삭제 (정적파일 & 메타데이터)
     * postman api : O
     * @param fileBoxId
     * @return
     */
    @Operation(summary = "파일 삭제", description = "파일을 삭제하는 API 입니다. 파일을 삭제하면 메타데이터 & 정적파일 모두 삭제 됩니다.")
    @DeleteMapping(value = "/delete", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> deleteFile(@RequestParam("fileBoxId") Long fileBoxId) {

        fileBoxService.deleteFile(fileBoxId);

        return ResponseEntity.ok("파일 삭제 성공");
    }
}
