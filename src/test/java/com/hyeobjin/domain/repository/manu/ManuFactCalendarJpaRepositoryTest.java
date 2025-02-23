package com.hyeobjin.domain.repository.manu;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@Slf4j
@SpringBootTest
@DisplayName("제조사/제품 테스트")
class ManuFactJpaRepositoryTest {

    @Autowired
    private ManuFactJpaRepository manuFactJpaRepository;

    @Test
    @DisplayName("조회 : 제조사에 해당하는 모든 제품의 PK 조회")
    void findSearchItemId() {

        // given
        Long manuId = 3L;

        // when
        List<Long> list = manuFactJpaRepository.selectItemId(manuId);

        // then
        System.out.println("list = " + list);
    }
}