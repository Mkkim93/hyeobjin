package com.hyeobjin.web.admin.board.api;

import com.hyeobjin.application.common.dto.board.CreateBoardDTO;
import com.hyeobjin.application.common.dto.board.UpdateBoardDTO;
import com.hyeobjin.application.common.service.board.BoardService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@Slf4j
@RestController
@Tag(name = "ADMIN_BOARD", description = "관리자 권한으로 게시판의 CRUD 기능을 관리하기 위한 REST API 입니다.")
@RequestMapping("/admin/board")
@RequiredArgsConstructor
public class AdminBoardApiController {

    private final BoardService boardService;

    /**
     * # 게시글작성 api (게시글 내용 & 파일)
     * postman api : O
     * @param createBoardDTO 게시글 객체
     * @param files 파일 객체
     * @return
     * @throws IOException
     */
    @Operation(summary = "게시글 작성", description = "게시글 & 파일 저장을 위한 API 입니다.")
    @PostMapping(
            consumes = MediaType.MULTIPART_FORM_DATA_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> save(@ModelAttribute CreateBoardDTO createBoardDTO,
                                       @RequestPart(value = "files", required = false) List<MultipartFile> files) throws IOException {

        boardService.saveBoard(createBoardDTO, files);
        return ResponseEntity.ok("board save success");
    }

    /**
     * 일반 사용자가 자신이 작성한 게시글을 삭제 (관리자용은 별도로 admin package 구성)
     * # swagger : O
     * @param boardId 게시글 번호를 기준으로 자신의 게시글을 삭제한다
     *                DB 데이터가 삭제되는 것이 아니라 특정 컬럼의 값을 업데이트하여 게시글이 조회되지 않도록 함
     *  ROLE : ADMIN
     * @return
     */
    @Operation(summary = "게시글 삭제", description = "게시글 삭제를 위한 API 입니다.")
    @PostMapping("/delete/{boardId}")
    public ResponseEntity<String> delete(@PathVariable("boardId") Long boardId) {
        boardService.delete(boardId);
        return ResponseEntity.ok("success delete board content");
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
