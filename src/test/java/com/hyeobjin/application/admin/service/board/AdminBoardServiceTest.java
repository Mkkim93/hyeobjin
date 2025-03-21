package com.hyeobjin.application.admin.service.board;

import com.hyeobjin.application.admin.dto.board.DetailAdminBoardDTO;
import com.hyeobjin.application.admin.dto.board.FindAdminBoardDTO;
import lombok.extern.slf4j.Slf4j;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.*;
import static org.assertj.core.api.Assertions.assertThat;

@Slf4j
@SpringBootTest
@Transactional
@DisplayName("관리자 게시글 조회 테스트")
class AdminBoardServiceTest {

    @Autowired
    private AdminBoardService adminBoardService;

    @Test
    @DisplayName("관리자가 모든 게시글을 조회 (페이징)")
    void findAllBoardList() {

        // given
        PageRequest pageable = PageRequest.of(0, 10);

        // when
        Page<FindAdminBoardDTO> result = adminBoardService.findByAllList(pageable);

        // then
        result.stream().forEach(System.out::println);
    }

    @Test
    @DisplayName("관리자가 게시글을 삭제 체크박스로 리스트 형태의 데이터를 삭제한다")
    void deleteAllBoard() {

        // given
        List<Long> boardIds = new ArrayList<>();
        boardIds.add(52L); // 파일 데이터가 없는 게시글 PK
        boardIds.add(10L); // 파일 데이터가 3개 존재하는 게시글 PK

        // when
        adminBoardService.deleteBoardAndFiles(boardIds);

        // then
        assertThat(boardIds).size().isEqualTo(0);
    }

    @Test
    @DisplayName("관리자가 게시글 번호를 기준으로 해당 글을 상세 조회 한다.")
    void findDetailAdmin() {

        // given
        Long boardId = 6L;

        // when
        DetailAdminBoardDTO detailBoard = adminBoardService.findDetailBoard(boardId);

        // then
        System.out.println("detailBoard = " + detailBoard);
        System.out.println("detailBoard.getAdminBoardFiles() = " + detailBoard.getAdminBoardFiles());

        assertThat(detailBoard.getBoardId()).isEqualTo(6L);
    }
}