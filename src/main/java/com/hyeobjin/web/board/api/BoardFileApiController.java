package com.hyeobjin.web.board.api;

import com.hyeobjin.application.service.board.BoardFileService;
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
@Tag(name = "BoardFileBox", description = "게시판 파일 관련 API")
@RestController
@RequestMapping("/boardFiles")
@RequiredArgsConstructor
public class BoardFileApiController {

    private final BoardFileService boardFileService;

    /**
     * 파일 데이터를 수정하면 기존 파일에서 파일데이터만 추가됩니다
     * 기존 파일 삭제는 delete() api 사용
     */
    // TODO 게시글 상세 페이지에서 자신이 작성한 파일을 수정
    @Operation(summary = "게시글 파일 수정", description = "게시글 상세 페이지에서 파일 게시물을 수정하는 API 입니다.")
    @PostMapping
    public ResponseEntity<String> addFile(@RequestParam("boardId") Long boardId,
                                          @RequestPart("files") List<MultipartFile> files) throws IOException {
        boardFileService.saveFileOnly(boardId, files);

        return ResponseEntity.ok("board file save success");
    }

    /**
     * 파일 데이터를 삭제하는 메타데이터 & 정적파일 모두 삭제
     */
    // TODO 게시글 상세 페이지에서 자신이 작성한 파일을 삭제
    @Operation(summary = "게시글 파일 삭제", description = "게시글 상세 페이지에서 해당 파일 데이터를 삭제하는 API 입니다.")
    @DeleteMapping
    public ResponseEntity<String> deleteFile(@RequestParam("boardId") Long boardId,
                                             @RequestParam("fileBoxId") Long fileBoxId) {

        boardFileService.delete(fileBoxId, boardId);

        return ResponseEntity.ok("게시글 내의 파일 삭제 성공");
    }
}
