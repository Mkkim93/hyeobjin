package com.hyeobjin.domain.repository;

import com.hyeobjin.application.dto.board.BoardDetailDTO;
import com.hyeobjin.domain.entity.board.Board;
import com.hyeobjin.domain.repository.board.BoardRepository;
import com.hyeobjin.domain.repository.board.BoardRepositoryImpl;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

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
    @DisplayName("BoardRepository connection Test")
    void findAll() {
        List<Board> result = boardRepository.findAll();
        result.stream().forEach(System.out::println);
        org.assertj.core.api.Assertions.assertThat(result).isNull();

    }

    @Test
    @DisplayName("queryDsl findDetail board content Only")
    void findOneBoardOnly() {
        Long boardId = 16L;

        BoardDetailDTO byBoardDetail = boardRepositoryImpl.findByBoardDetail(boardId);

        System.out.println("byBoardDetail = " + byBoardDetail);
    }
}