package com.hyeobjin.domain.repository.item.type;

import com.hyeobjin.application.common.dto.item.type.ItemTypeDTO;
import lombok.extern.slf4j.Slf4j;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@Slf4j
@SpringBootTest
class itemTypeQueryRepositoryTest {

    @Autowired
    ItemTypeQueryRepository itemTypeQueryRepository;

    @Test
    @DisplayName("메인 폼에서 제조사 클릭 시 제품 타입 조회")
    void findItemType() {
        Long manuId = 1L;
        List<ItemTypeDTO> result = itemTypeQueryRepository.findByCategoryByManuId(manuId);

        result.stream().forEach(System.out::println);

        Assertions.assertThat(result);
    }

}