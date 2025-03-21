package com.hyeobjin.application.admin.service.board;

import com.hyeobjin.application.admin.dto.board.FindAdminBoardDTO;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.transaction.annotation.Transactional;

import static org.assertj.core.api.Assertions.*;

@Slf4j
@Transactional
@DisplayName("관리자 게시글 조회 테스트")
@SpringBootTest
class AdminBoardReplyServiceTest {

    @Autowired
    private AdminBoardReplyService adminBoardReplyService;

    @Test
    @DisplayName("관리자가 검색으로 게시글 조회_페이징")
    void findAllSearchKeywordBoardList() {

        // given
        PageRequest pageRequest = PageRequest.of(0, 10);

        // when
        Page<FindAdminBoardDTO> result = adminBoardReplyService.searchKeywordList("07", pageRequest);

        // then
        result.stream().forEach(System.out::println);
        assertThat(result.getTotalElements()).isEqualTo(2);
    }
}