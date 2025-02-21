package com.hyeobjin.application.service.board;

import com.hyeobjin.application.admin.service.board.AdminBoardService;
import com.hyeobjin.application.common.dto.board.BoardListDTO;
import com.hyeobjin.application.common.dto.board.CreateBoardDTO;
import com.hyeobjin.application.common.dto.board.UpdateBoardDTO;
import com.hyeobjin.application.common.service.board.BoardService;
import com.hyeobjin.domain.entity.board.Board;
import com.hyeobjin.domain.repository.board.BoardRepository;
import jakarta.persistence.EntityManager;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

import java.io.IOException;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

@Slf4j
@SpringBootTest
class BoardServiceTest {

    @Autowired
    private BoardService boardService;

    @Autowired
    private AdminBoardService adminBoardService;

    @Autowired
    private BoardRepository boardRepository;

    @Autowired
    private EntityManager em;

    @Test
    @DisplayName("게시글 목록 조회")
    void findAll() {
        String boardType = "NOTICE";
        PageRequest pageable = PageRequest.of(0, 10);
        Page<BoardListDTO> boardList = boardService.findAll(pageable, boardType);

        boardList.stream().forEach(System.out::println);
    }

    @Test
    @DisplayName("게시글 저장")
    void saveBoard() throws IOException {

        CreateBoardDTO createBoardDTO = new CreateBoardDTO();
        createBoardDTO.setBoardTitle("게시글 제목 테스트06");
        createBoardDTO.setBoardContent("게시글 내용 테스트06");
        createBoardDTO.setUsersId(3L);

        adminBoardService.saveBoard(createBoardDTO, null, null);

        Board savedBoard = boardRepository.findByBoardTitle("게시글 제목 테스트06");

        assertNotNull(savedBoard);
        assertEquals("게시글 제목 테스트06", savedBoard.getBoardTitle());
        assertEquals("게시글 내용 테스트06", savedBoard.getBoardContent());
        assertEquals(3L, savedBoard.getUsers().getId());
    }

    @Test
    @DisplayName("게시글 삭제")
    void delete() {
        Long boardId = 10L;

        boardService.delete(boardId);

        Board board = boardRepository.findById(boardId).get();

        assertThat(board.getBoardYN()).isEqualTo("Y");
    }

    @Test
    @DisplayName("게시글 제목 수정 (내용은 유지)")
    void updateTitle() throws IOException {
        UpdateBoardDTO updateBoardDTO = new UpdateBoardDTO();
        updateBoardDTO.setBoardId(19L);
        updateBoardDTO.setBoardTitle("19 게시글 제목 수정");

        adminBoardService.update(updateBoardDTO, null);

        Board board = boardRepository.findById(19L).get();

        assertThat(updateBoardDTO.getBoardTitle()).isEqualTo(board.getBoardTitle());
        assertThat(updateBoardDTO.getBoardId()).isEqualTo(board.getId());
    }

    @Test
    @DisplayName("게시글 내용 수정 (제목은 유지)")
    void updateContent() throws IOException {
        UpdateBoardDTO updateBoardDTO = new UpdateBoardDTO();
        updateBoardDTO.setBoardId(19L);
        updateBoardDTO.setBoardContent("19 게시글 내용 수정");

        adminBoardService.update(updateBoardDTO, null);

        Board board = boardRepository.findById(19L).get();

        assertThat(updateBoardDTO.getBoardContent()).isEqualTo(board.getBoardContent());
        assertThat(updateBoardDTO.getBoardId()).isEqualTo(board.getId());
    }
}