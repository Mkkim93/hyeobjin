package com.hyeobjin.domain.repository;

import com.hyeobjin.application.admin.dto.board.DetailAdminBoardDTO;
import com.hyeobjin.domain.entity.board.Board;
import com.hyeobjin.domain.repository.board.BoardRepository;
import com.hyeobjin.domain.repository.board.BoardRepositoryImpl;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

@Slf4j
@SpringBootTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class BoardRepositoryTest {

    @Autowired
    BoardRepository boardRepository;

    @Autowired
    BoardRepositoryImpl boardRepositoryImpl;

    @Test
    @DisplayName("사용자 게시글 전체 조회 (페이징) boardYN = Y 만 조회")
    void findAll() {
        List<Board> result = boardRepository.findAll();

        result.stream().forEach(System.out::println);

        assertThat(result).isNull();
    }

    @Test
    @DisplayName("사용자 게시글 검색 조회 (페이징) boardYN = Y 만 조회")
    void findSearchKeywordAll() {
        PageRequest pageRequest = PageRequest.of(0, 10);
        String boardType = "NOTICE";
        Page<Board> result = boardRepository.findByBoardYNAndBoardTitleContainingAndBoardType("Y", "07", boardType, pageRequest);

        result.stream().forEach(System.out::println);

        assertThat(result.getTotalElements()).isEqualTo(1);
    }

    @Test
    @DisplayName("관리자 게시글 전체 조회 (페이징) boardYN 관계 없이 조회")
    void findAllBoardList() {
        Page<Board> result = boardRepository.findByAllBoardListAdmin(PageRequest.of(0, 10));

        result.stream().forEach(System.out::println);

        assertThat(result).isNotNull();
    }

    @Test
    @DisplayName("관리자 게시글 검색 조회 (페이징) boardYN 관계 없이 조회")
    void findSearchKeywordAdminAll() {

        PageRequest pageRequest = PageRequest.of(0, 10);

        Page<Board> result = boardRepository.findByBoardTitleContaining(pageRequest, "07");

        result.stream().forEach(System.out::println);

        assertThat(result.getTotalElements()).isEqualTo(2);

    }

    @Test
    @DisplayName("게시글의 데이터 영구 삭제, (파일 데이터도 함께 삭제)")
    void deleteByIdsAllBatch() {
        List<Long> boardIds = new ArrayList<>();

        boardIds.add(6L);
        boardRepository.deleteAllByIdInBatch(boardIds);
    }

    @Test
    @DisplayName("관리자가 게시글 상세 조회 (일반 사용자보다 조회 데이터 더 많음)")
    void findDetailBoardIsAdmin() {

        Long boardId = 5L;
        DetailAdminBoardDTO result = boardRepositoryImpl.findByBoardDetailAdmin(boardId);

        System.out.println("result = " + result);
    }

    @Test
    @DisplayName("관리자 폼 메인에서 상위 2개의 최근 게시글만 가지고 오는 쿼리")
    void findTop2TitleByUpdate() {
        List<Board> result = boardRepository.findTop2ByOrderByBoardUpdateDesc();

        result.stream().forEach(System.out::println);
    }
}