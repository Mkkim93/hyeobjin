package com.hyeobjin.web.admin.board.api;

import com.hyeobjin.application.admin.dto.board.DetailAdminBoardDTO;
import com.hyeobjin.application.admin.dto.board.FindAdminBoardDTO;
import com.hyeobjin.application.admin.service.board.AdminBoardReplyService;
import com.hyeobjin.application.admin.service.board.AdminBoardService;
import com.hyeobjin.application.common.dto.board.CreateBoardDTO;
import com.hyeobjin.application.common.dto.board.UpdateBoardDTO;
import com.hyeobjin.application.common.service.board.BoardService;
import com.hyeobjin.jwt.JwtUtil;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@Slf4j
@RestController
@Tag(name = "ADMIN_BOARD", description = "관리자 권한으로 게시판의 CRUD 기능을 관리하기 위한 REST API 입니다.")
@RequestMapping("/admin/boards")
@RequiredArgsConstructor
public class AdminBoardApiController {

    private final JwtUtil jwtUtil;
    private final BoardService boardService;
    private final AdminBoardService adminBoardService;
    private final AdminBoardReplyService adminBoardReplyService;

    @Operation(summary = "관리자 게시글 조회", description = "관리자가 모든 게시글 목록을 조회하기 위한 API 입니다.")
    @GetMapping
    public ResponseEntity<Page<FindAdminBoardDTO>> findAll(@RequestParam(name = "page", defaultValue = "0") int page,
                                                           @RequestParam(name = "size", defaultValue = "10") int size,
                                                           @RequestParam(value = "searchKeyword", required = false) String searchKeyword) {

        Page<FindAdminBoardDTO> list = null;
        PageRequest pageRequest = PageRequest.of(page, size);

        if (searchKeyword == null) {

           list = adminBoardService.findByAllList(pageRequest);
        }

        if (searchKeyword != null) {
            list = adminBoardReplyService.searchKeywordList(searchKeyword, pageRequest);
        }
        return ResponseEntity.ok(list);
    }

    /**
     * # 게시글작성 api (게시글 내용 & 파일)
     * postman api : O
     * @param createBoardDTO 게시글 객체
     * @param files 파일 객체
     * @return
     * @throws IOException
     */
    @Operation(summary = "게시글 작성", description = "게시글 & 파일 저장을 위한 API 입니다.")
    @PostMapping
    public ResponseEntity<Long> save(@RequestBody CreateBoardDTO createBoardDTO,
                                       @RequestPart(value = "files", required = false) List<MultipartFile> files, HttpServletRequest request) throws IOException {

        String authToken = request.getHeader("Authorization");
        createBoardDTO.setAuthToken(authToken);

        Long boardId = adminBoardService.saveBoard(createBoardDTO, files);

        log.info("boardId={}", boardId);

        return ResponseEntity.ok(boardId);
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
        adminBoardService.update(updateBoardDTO);
        return ResponseEntity.ok().build();
    }

    /**
     * 관리자가 제품을 삭제하면 해당 제품과 연관된 파일 데이터도 함께 영구적으로 삭제됩니다.
     * swagger : O
     * ROLE : ADMIN
     * @param boardIds 관리자가 제품을 영구적으로 삭제하기 위해 클라이언트 체크박스로 제품의 PK 를 리스트 형태로 요청합니다.
     */
    @Operation(summary = "게시글 삭제", description = "관리자가 게시글을 삭제한 모든 게시글과 파일 데이터가 영구적으로 삭제되는 API 입니다.")
    @DeleteMapping
    public ResponseEntity<Void> delete(@RequestBody List<Long> boardIds) {
        try {
            adminBoardService.deleteBoardAndFiles(boardIds);
            return ResponseEntity.ok().build();
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(null);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }
    /**
     * 일반 사용자가 자신이 작성한 게시글을 삭제 (관리자용은 별도로 admin package 구성)
     * # swagger : O
     * @param boardId 게시글 번호를 기준으로 자신의 게시글을 삭제한다
     *                DB 데이터가 삭제되는 것이 아니라 특정 컬럼의 값을 업데이트하여 게시글이 조회되지 않도록 함
     *  ROLE : ADMIN
     *                // TODO 안쓸듯
     * @return
     */
    @Operation(summary = "게시글 삭제", description = "게시글 삭제를 위한 API 입니다.")
    @PostMapping("/delete/{boardId}")
    public ResponseEntity<String> delete(@PathVariable("boardId") Long boardId) {
        boardService.delete(boardId);
        return ResponseEntity.ok("success delete board content");
    }

    @Operation(summary = "게시글 상세 조회", description = "관리자가 게시글을 상세 조회 하는 API 입니다.")
    @GetMapping("/detail/{boardId}")
    public ResponseEntity<DetailAdminBoardDTO> detail(@PathVariable("boardId") Long boardId) {
       return ResponseEntity.ok(adminBoardService.findDetailBoard(boardId));
    }

}
