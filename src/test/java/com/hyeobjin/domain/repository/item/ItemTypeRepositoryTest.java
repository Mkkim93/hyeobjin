package com.hyeobjin.domain.repository.item;

import com.hyeobjin.application.common.dto.item.type.FindItemTypeDTO;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@Slf4j
@SpringBootTest
class ItemTypeRepositoryTest {

    @Autowired
    ItemTypeRepository itemTypeRepository;

    @Test
    @DisplayName("제품 타입으로 제품 이름 리스트 조회")
    void findByItemNameForType() {

        Long itemTypeId = 1L;
        Long manuId = 1L;

        List<FindItemTypeDTO> result = itemTypeRepository.findByItemNameList(itemTypeId, manuId);

        result.stream().forEach(System.out::println);
    }

    @Test
    void test() {
        List<FindItemTypeDTO> result = itemTypeRepository.findByItemNames(1L);

        result.stream().forEach(System.out::println);
    }
}