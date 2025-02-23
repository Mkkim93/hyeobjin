package com.hyeobjin.domain.repository.board;

import com.hyeobjin.application.admin.dto.board.DetailAdminBoardDTO;
import com.hyeobjin.application.common.dto.board.BoardDetailDTO;
import com.hyeobjin.application.common.dto.board.UpdateBoardDTO;
import com.hyeobjin.domain.entity.board.Board;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.transaction.annotation.Transactional;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@Transactional
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@DisplayName("공지사항/FAQ 테스트(상세 조회)")
class BoardRepositoryImplTest {

    @Autowired
    private BoardRepositoryImpl boardRepositoryImpl;

    @Autowired
    private BoardRepository boardRepository;

    @Autowired
    private EntityManager entityManager;

    @Test
    @DisplayName("상세 조회 : 게시글_파일_사용자")
    void findByBoardDetail() {

        // given
        Long boardId = 1L;

        // when
        BoardDetailDTO result = boardRepositoryImpl.findByBoardDetail(boardId);

        // then
        assertThat(result.getBoardId()).isEqualTo(boardId);
        assertThat(result.getBoardFiles()).isNotNull();
        assertThat(result.getWriter()).isNotNull();
    }

    @Test
    @DisplayName("수정 : 입력한 데이터만 수정 (null 값은 기존 데이터 유지)")
    void updateBoard() {
        // given
        Long boardId = 3L;
        String boardTitle = "제목 테스트";

        // when (조회)
        Board findBoard = boardRepository.findById(boardId)
                .orElseThrow(() -> new EntityNotFoundException("해당 게시글 없음"));

        // then (수정 전 검증)
        assertThat(findBoard.getBoardTitle()).isEqualTo(boardTitle);

        // given (update)
        UpdateBoardDTO updateBoardDTO = new UpdateBoardDTO();
        updateBoardDTO.setBoardId(boardId);
        updateBoardDTO.setBoardTitle("제목 테스트 수정");
        updateBoardDTO.setBoardContent(null); // null 입력 시 기존 값 유지 확인

        // when (update)
        boardRepositoryImpl.updateBoard(updateBoardDTO);

        // 강제 반영 후 영속성 컨텍스트 초기화
        entityManager.flush();
        entityManager.clear();

        // then (update)
        Board updatedBoard = boardRepository.findById(boardId)
                .orElseThrow(() -> new EntityNotFoundException("수정 후 게시글 없음"));

        assertThat(updatedBoard.getBoardTitle()).isEqualTo(updateBoardDTO.getBoardTitle());
        assertThat(updatedBoard.getBoardContent()).isEqualTo(findBoard.getBoardContent());
    }

    @Test
    @DisplayName("상세 조회 (관리자) : 게시글 상세 조회 (비공개 글 포함)")
    void findByBoardDetailAdmin() {

        // given
        Long boardId = 3L;

        // when
        DetailAdminBoardDTO result = boardRepositoryImpl.findByBoardDetailAdmin(boardId);

        // then
        assertThat(result.getBoardYN()).isEqualTo("N");
    }
}