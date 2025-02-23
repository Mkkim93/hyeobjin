package com.hyeobjin.domain.repository.item;

import com.hyeobjin.application.common.dto.item.type.FindItemTypeDTO;
import lombok.extern.slf4j.Slf4j;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Slf4j
@DataJpaTest
@Transactional
@DisplayName("제품 타입 테스트")
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class ItemTypeRepositoryTest {

    @Autowired
    private ItemTypeRepository itemTypeRepository;

    @Test
    @DisplayName("제품 타입으로 제품 이름 리스트 조회")
    void findByItemNameForType() {

        // given
        Long itemTypeId = 1L;
        Long manuId = 1L;

        // when
        List<FindItemTypeDTO> result = itemTypeRepository.findByItemNameList(itemTypeId, manuId);

        // then
        Assertions.assertThat(result).allMatch(s -> s.getItemTypeId().equals(itemTypeId));
    }
}