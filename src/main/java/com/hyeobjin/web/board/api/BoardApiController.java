package com.hyeobjin.web.board.api;

import com.hyeobjin.application.dto.board.*;
import com.hyeobjin.application.service.board.BoardReplyService;
import com.hyeobjin.application.service.board.BoardService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@Slf4j
@Tag(name = "board", description = "게시판 관련 API")
@RestController
@RequestMapping("/boards")
@RequiredArgsConstructor
public class BoardApiController {

    private final BoardService boardService;
    private final BoardReplyService boardReplyService;

    /**
     * # 게시글을 파일 데이터와 저장
     * postman api : O
     * @param createBoardDTO 게시글 객체
     * @param files 파일 객체
     * @return
     * @throws IOException
     */
    @Operation(summary = "게시글 저장", description = "게시글 & 파일 저장을 위한 API 입니다.")
    @PostMapping(
            consumes = MediaType.MULTIPART_FORM_DATA_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> save(@ModelAttribute BoardFileDTO createBoardDTO,
                                       @RequestPart(value = "files", required = false) List<MultipartFile> files) throws IOException {

        boardService.save(createBoardDTO, files);
        return ResponseEntity.ok("board save success");
    }

    /**
     * 게시글 전체 목록 조회
     * 검색 기능
     * 페이징 쿼리
     * swagger : O
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

        Page<BoardListDTO> list = null;

        if (searchKeyword != null) {
            list = boardReplyService.searchKeywordList(searchKeyword, pageRequest);
        } else {
            list = boardService.findAll(pageRequest);
        }
        return ResponseEntity.ok(list);
    }

    /**
     * 일반 사용자가 자신이 작성한 게시글을 삭제 (관리자용은 별도로 admin package 구성)
     * # swagger : O
     * @param boardId 게시글 번호를 기준으로 자신의 게시글을 삭제한다
     *                DB 데이터가 삭제되는 것이 아니라 특정 컬럼의 값을 업데이트하여 게시글이 조회되지 않도록 함
     * @return
     */
    @Operation(summary = "게시글 삭제", description = "게시글 삭제를 위한 API 입니다.")
    @PostMapping("/delete/{boardId}")
    public ResponseEntity<String> delete(@PathVariable("boardId") Long boardId) {
        boardService.delete(boardId);
        return ResponseEntity.ok("success delete board content");
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

    /**
     * 자신이 작성한 게시물을 수정할 수 있다
     * @param updateBoardDTO 클라이언트가 해당 객체를 통해 수정된 제목과 내용을 서버로 전송한다.
     *                       파일 데이터의 수정은 별도의 api 로 파일 수정 기능 구현
     *  # swagger : O
     * @return
     */
    @Operation(summary = "게시글 수정", description = "게시글 수정을 위한 API 입니다.")
    @PostMapping("/update")
    public ResponseEntity<Void> update(@RequestBody UpdateBoardDTO updateBoardDTO)    {
        boardService.update(updateBoardDTO);
        return ResponseEntity.ok().build();
    }
}
