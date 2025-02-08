package com.hyeobjin.web.admin.item.api;


import com.hyeobjin.application.admin.dto.file.UpdateItemFileDTO;
import com.hyeobjin.application.admin.service.file.AdminItemFileService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@Slf4j
@RestController
@Tag(name = "ADMIN_ITEM_FILE", description = "관리자 권한으로 제품의 파일 관리를 위한 REST API 입니다.")
@RequestMapping("/item/files")
@RequiredArgsConstructor
public class AdminItemFileApiController {

    private final AdminItemFileService adminItemFileService;

    /**
     * 제품 파일 삭제
     */
    /**
     * 현재 등록중이거나 등록된 파일의 데이터 삭제 (정적파일 & 메타데이터)
     * postman api : O
     * @param fileBoxId 삭제할 file 의 기본키
     * @return
     */
    @Operation(summary = "파일 삭제", description = "fileBoxId 값을 기준으로 파일을 삭제 API 입니다. (메타데이터 & 정적파일 모두 삭제)")
    @DeleteMapping
    public ResponseEntity<String> deleteFile(@RequestParam("fileBoxId") Long fileBoxId) {

        adminItemFileService.deleteFileFirst(fileBoxId);

        return ResponseEntity.ok("파일 삭제 성공");
    }

    /**
     * 제품 파일 수정
     */
    @Operation(summary = "파일 수정", description = "fileBoxId 값을 기준으로 기존의 파일을 업데이트 하기 위한 API 입니다.")
    @PostMapping
    public ResponseEntity<?> updateFile(@RequestParam("fileBoxId") Long fileBoxId,
                                        @RequestPart("files") MultipartFile file) throws IOException {
//        adminItemFileService.updateFile(fileBoxId, file);
        return ResponseEntity.ok("파일 수정 성공");
    }

    /**
     * 제품 파일 등록
     */
    @Operation(summary = "파일 추가 등록", description = "itemId 값을 기준으로 기존의 제품에 새로운 파일 추가를 위한 API 입니다.")
    @PostMapping("/add")
    public ResponseEntity<?> addFile(@ModelAttribute UpdateItemFileDTO updateItemFileDTO,
                                     @RequestPart("files") List<MultipartFile> file) throws IOException {
        adminItemFileService.fileAddSave(updateItemFileDTO, file);
        return ResponseEntity.ok("파일 등록 성공");
    }
}
