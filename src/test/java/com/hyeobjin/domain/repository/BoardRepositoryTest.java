package com.hyeobjin.domain.repository;


import com.hyeobjin.domain.entity.Board;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@Slf4j
@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class BoardRepositoryTest {

    @Autowired BoardRepository boardRepository;

    @Test
    @DisplayName("BoardRepository Test")
    void findAll() {
        List<Board> result = boardRepository.findAll();
        result.stream().forEach(System.out::println);
        org.assertj.core.api.Assertions.assertThat(result).isNull();

    }
}