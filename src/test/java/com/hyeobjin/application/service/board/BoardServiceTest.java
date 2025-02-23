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
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

@Slf4j
@SpringBootTest
@Transactional
@DisplayName("게시판 테스트")
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
    @DisplayName("조회 : 게시글 목록 조회_페이징")
    void findAll() {

        // given
        String boardType = "NOTICE";
        PageRequest pageable = PageRequest.of(0, 10);

        // when
        Page<BoardListDTO> boardList = boardService.findAll(pageable, boardType);

        // then
        boardList.stream().forEach(System.out::println);
    }

    @Test
    @DisplayName("등록 : 게시글 저장")
    void saveBoard() throws IOException {

        // given
        CreateBoardDTO createBoardDTO = new CreateBoardDTO();
        createBoardDTO.setBoardTitle("게시글 제목 테스트06");
        createBoardDTO.setBoardContent("게시글 내용 테스트06");
        createBoardDTO.setUsersId(3L); // REST API 환경에서는 서버에 저장된 USERID 를 꺼냄 (SecurityContextHolder())

        // when
        Long saveBoard = adminBoardService.saveBoard(createBoardDTO, null, createBoardDTO.getUsersId());

        // then (성공 시 게시글 ID 반환)
        assertThat(saveBoard).isNotNull();

    }

    @Test
    @DisplayName("수정 : 게시글 제목 수정 (내용은 유지)")
    void updateTitle() throws IOException {

        // given
        UpdateBoardDTO updateBoardDTO = new UpdateBoardDTO();
        updateBoardDTO.setBoardId(3L);
        updateBoardDTO.setBoardTitle("19 게시글 제목 수정");

        // when
        adminBoardService.update(updateBoardDTO, null);
        em.flush();
        em.clear();

        Board board = boardRepository.findById(3L).get();

        // then
        assertThat(updateBoardDTO.getBoardTitle()).isEqualTo(board.getBoardTitle());
        assertThat(updateBoardDTO.getBoardId()).isEqualTo(board.getId());
    }

    @Test
    @DisplayName("수정 : 게시글 내용 수정 (제목은 유지)")
    void updateContent() throws IOException {

        // given
        UpdateBoardDTO updateBoardDTO = new UpdateBoardDTO();
        updateBoardDTO.setBoardId(3L);
        updateBoardDTO.setBoardContent("19 게시글 내용 수정");

        // when
        adminBoardService.update(updateBoardDTO, null);

        Board board = boardRepository.findById(3L).get();

        // then
        assertThat(updateBoardDTO.getBoardContent()).isEqualTo(board.getBoardContent());
        assertThat(updateBoardDTO.getBoardId()).isEqualTo(board.getId());
    }
}