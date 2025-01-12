package com.hyeobjin.application.service.board;

import com.hyeobjin.application.dto.board.CreateBoardDTO;
import com.hyeobjin.domain.entity.board.Board;
import com.hyeobjin.domain.repository.board.BoardRepository;
import jakarta.persistence.EntityManager;
import lombok.extern.slf4j.Slf4j;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

@Slf4j
@SpringBootTest
class BoardServiceTest {

    @Autowired
    private BoardService boardService;

    @Autowired
    private BoardRepository boardRepository;

    @Autowired
    private EntityManager em;

    @Test
    @DisplayName("게시글 저장")
    @Transactional
    void saveBoard() {
        // TODO
        CreateBoardDTO createBoardDTO = new CreateBoardDTO();
        createBoardDTO.setBoardTitle("게시글 제목 테스트05");
        createBoardDTO.setBoardContent("게시글 내용 테스트05");
        createBoardDTO.setUsersId(3L);

        boardService.save(createBoardDTO);

        em.flush();
        em.clear();

        Board savedBoard = boardRepository.findByBoardTitle("게시글 제목 테스트05");

        assertNull(savedBoard);
        assertEquals("게시글 제목 테스트05", savedBoard.getBoardTitle());
        assertEquals("게시글 내용 테스트05", savedBoard.getBoardContent());
        assertEquals(3L, savedBoard.getUsers().getId());
    }

}