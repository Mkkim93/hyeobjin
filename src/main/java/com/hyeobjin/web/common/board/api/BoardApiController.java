package com.hyeobjin.web.common.board.api;

import com.hyeobjin.application.common.dto.board.BoardDetailDTO;
import com.hyeobjin.application.common.dto.board.BoardListDTO;
import com.hyeobjin.application.common.service.board.BoardReplyService;
import com.hyeobjin.application.common.service.board.BoardService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@Tag(name = "COMMON_BOARD", description = "게시판 관련 API")
@RestController
@RequestMapping("/boards")
@RequiredArgsConstructor
public class BoardApiController {

    private final BoardService boardService;
    private final BoardReplyService boardReplyService;

    /**
     * 게시글 전체 목록 조회
     * 검색 기능
     * 페이징 쿼리
     * swagger : O
     * ROLE : COMMON
     * @param page 0
     * @param size 10
     * @param searchKeyword 검색할 키워드 (제목 or 내용)
     * @return
     */
    @Operation(summary = "게시글 목록 조회", description = "게시글 전체 목록을 조회하는 API 입니다.")
    @GetMapping
    public ResponseEntity<Page<BoardListDTO>> findAll(@RequestParam(value = "page", defaultValue = "0") int page,
                                                      @RequestParam(value = "size", defaultValue = "10") int size,
                                                      @RequestParam(value = "searchKeyword", required = false) String searchKeyword) {
        PageRequest pageRequest = PageRequest.of(page, size);

        Page<BoardListDTO> boardList = null;

        if (searchKeyword != null) {
            boardList = boardReplyService.searchKeywordList(searchKeyword, pageRequest);
        } else {
            boardList = boardService.findAll(pageRequest);
        }
        return ResponseEntity.ok(boardList);
    }

    /**
     * 게시글 번호를 기준으로 게시글 제목을 클릭하면 상세 조회로 모든 상세 내용을 조회.
     * # swagger : O
     * @param boardId 게시글 번호
     * @return
     */
    @Operation(summary = "게시글 상세 조회", description = "게시글 상세 조회를 위한 API 입니다.")
    @GetMapping("/detail")
    public ResponseEntity<BoardDetailDTO> detail(@RequestParam("boardId") Long boardId) {

        return ResponseEntity.ok(boardService.findDetail(boardId));
    }
}
