package com.hyeobjin.domain.repository.board;

import com.hyeobjin.domain.entity.board.Board;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@Transactional
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@DisplayName("공지사항/FAQ 테스트")
class BoardRepositoryTest {

    @Autowired
    private BoardRepository boardRepository;

    @Test
    @DisplayName("조회 : 공개 게시글_게시글 타입(FAQ/NOTICE)_페이징")
    void findByAllBoardList() {

        // given
        PageRequest pageRequest = PageRequest.of(0, 5);
        String boardType = "NOTICE";

        // when
        Page<Board> result = boardRepository.findByAllBoardList(pageRequest, boardType);
        List<Board> list = result.stream().toList();

        // then
        list.forEach(System.out::println);
    }

    @Test
    @DisplayName("조회 : 검색 키워드_게시글 타입_공개 게시글_페이징")
    void findByBoardYNAndBoardTitleContainingAndBoardType() {

        // given
        PageRequest pageRequest = PageRequest.of(0, 5);
        String searchKeyword = "공지";
        String boardTypeNotice = "NOTICE";
        String boardTypeFAQ = "FAQ";
        String boardYN = "Y";

        // when
        Page<Board> searchBoardByNotice = boardRepository.findByBoardYNAndBoardTitleContainingAndBoardType(boardYN, searchKeyword, boardTypeNotice, pageRequest);
        Page<Board> searchBoardByFAQ = boardRepository.findByBoardYNAndBoardTitleContainingAndBoardType(boardYN, searchKeyword, boardTypeFAQ, pageRequest);

        List<Board> noticeList = searchBoardByNotice.getContent();
        List<Board> faqList = searchBoardByFAQ.getContent();

        // then
        assertThat(noticeList).isNotEmpty();
        assertThat(faqList).isEmpty();

        assertThat(noticeList).allMatch(board -> board.getBoardType().equals(boardTypeNotice));
        assertThat(faqList).allMatch(board -> board.getBoardType().equals(boardTypeFAQ));

        assertThat(noticeList).doesNotContainNull();
        assertThat(faqList).doesNotContainNull();
    }

    @Test
    @DisplayName("조회(관리자) : 모든 게시글 전체 조회_페이징_최근 작성일 내림차순")
    void findByAllBoardListAdmin() {
        // given
        PageRequest pageRequest = PageRequest.of(0, 5);

        // when
        Page<Board> result = boardRepository.findByAllBoardListAdmin(pageRequest);
        List<Board> content = result.getContent();

        // then
        assertThat(content.size()).isEqualTo(2);
    }

    @Test
    @DisplayName("조회(관리자) : 모든 게시글 검색 조회_페이징")
    void findByBoardTitleContaining() {

        // given
        PageRequest pageRequest = PageRequest.of(0, 5);
        String searchKeyword = "공지";

        // when
        Page<Board> result = boardRepository.findByBoardTitleContaining(pageRequest, searchKeyword);
        List<Board> content = result.getContent();

        // then
        assertThat(content).allMatch(board -> board.getBoardTitle().contains(searchKeyword));
    }

    @Test
    @DisplayName("조회(관리자) : 게시글 2건 조회_최근 작성일")
    void findTop2ByOrderByBoardUpdateDesc() {

        // given
        int boardCount = 2;

        // when
        List<Board> result = boardRepository.findTop2ByOrderByBoardUpdateDesc();

        // then
        assertThat(result).size().isEqualTo(boardCount);
    }

    @Test
    @DisplayName("조회 수 증가 : 게시글 조회_조회 수 증가")
    void updateBoardViewCount() {

        // given
        Long boardId = 1L;

        // when
        int updateCount = boardRepository.updateBoardViewCount(boardId);

        // then
        assertThat(updateCount).isEqualTo(1);
    }
}