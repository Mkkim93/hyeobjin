package com.hyeobjin.web.admin.board.api;

import com.hyeobjin.application.admin.dto.board.DetailAdminBoardDTO;
import com.hyeobjin.application.admin.dto.board.FindAdminBoardDTO;
import com.hyeobjin.application.admin.service.board.AdminBoardReplyService;
import com.hyeobjin.application.admin.service.board.AdminBoardService;
import com.hyeobjin.application.admin.service.board.BoardAuthService;
import com.hyeobjin.application.common.dto.board.CreateBoardDTO;
import com.hyeobjin.application.common.dto.board.UpdateBoardDTO;
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
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@RestController
@Tag(name = "ADMIN_BOARD", description = "관리자 권한으로 게시판의 CRUD 기능을 관리하기 위한 REST API 입니다.")
@RequestMapping("/admin/boards")
@RequiredArgsConstructor
public class AdminBoardApiController {

    private final BoardAuthService boardAuthService;
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
    public ResponseEntity<Long> save(@RequestPart(value = "createBoardDTO") CreateBoardDTO createBoardDTO,
                                     @RequestPart(value = "files", required = false) List<MultipartFile> files,
                                     HttpServletRequest request) throws IOException {
        String authToken = request.getHeader("Authorization");
        Long userId = boardAuthService.findByUserId(authToken);
        Long boardId = adminBoardService.saveBoard(createBoardDTO, files, userId);
        log.info("boardId={}", boardId);
        return ResponseEntity.ok(boardId);
    }

    /**
     * 관리자가 수정할 게시글을 호출하는 API
     * (vue 컴포넌트로 변경할지 고민 props 사용하면 API 호출 업이 기존 상세 페이지 데이터 재사용 가능)
     * @param boardId
     * @return
     */
    @Operation(summary = "수정할 게시글 조회", description = "수정할 게시글을 폼을 랜더링 하기 위한 API 입니다.")
    @GetMapping("/modify")
    public ResponseEntity<DetailAdminBoardDTO> findByModifyBoard(@RequestParam("boardId") Long boardId) {
        return ResponseEntity.ok(adminBoardService.findDetailBoard(boardId));
    }

    /**
     * 자신이 작성한 게시물을 수정할 수 있다
     * @param updateBoardDTO 클라이언트가 해당 객체를 통해 수정된 제목과 내용을 서버로 전송한다.
     *                       파일 데이터의 수정은 별도의 api 로 파일 수정 기능 구현
     *  # swagger : O
     * @return
     */
    @Operation(summary = "게시글 수정", description = "게시글 수정을 위한 API 입니다.")
    @PostMapping(value = "/update", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<?> update(@RequestPart(name = "updateBoardDTO") UpdateBoardDTO updateBoardDTO,
                                    @RequestPart(value = "files", required = false) List<MultipartFile> file) throws IOException {

        if (file == null) {
            file = new ArrayList<>();
        }

        Long updatedId = adminBoardService.update(updateBoardDTO, file);
        return ResponseEntity.ok(updatedId);
    }


    /**
     * 관리자가 제품을 삭제하면 해당 제품과 연관된 파일 데이터도 함께 영구적으로 삭제됩니다.
     * swagger : O
     * ROLE : ADMIN
     * @param boardIds 관리자가 제품을 영구적으로 삭제하기 위해 클라이언트 체크박스로 제품의 PK 를 리스트 형태로 요청합니다.
     */
    @Operation(summary = "게시글 삭제", description = "관리자가 게시글을 삭제한 모든 게시글과 파일 데이터가 영구적으로 삭제되는 API 입니다.")
    @DeleteMapping
    public ResponseEntity<?> delete(@RequestBody List<Long> boardIds) {
        try {
            adminBoardService.deleteBoardAndFiles(boardIds);
            return ResponseEntity.ok().build();
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(null);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    @Operation(summary = "게시글 상세 조회", description = "관리자가 게시글을 상세 조회 하는 API 입니다.")
    @GetMapping("/detail/{boardId}")
    public ResponseEntity<DetailAdminBoardDTO> detail(@PathVariable("boardId") Long boardId) {
       return ResponseEntity.ok(adminBoardService.findDetailBoard(boardId));
    }
}
