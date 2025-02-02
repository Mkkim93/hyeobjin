package com.hyeobjin.domain.repository.manu;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.*;

@Slf4j
@SpringBootTest
class ManuFactCalendarJpaRepositoryTest {

    @Autowired ManuFactJpaRepository manuFactJpaRepository;

    @Test
    @DisplayName("제조사에 해당하는 제품의 pk 여러개 조회")
    void findSearchItemId() {
        List<Long> list = manuFactJpaRepository.selectItemId(5L);

        System.out.println("list = " + list);

    }
}