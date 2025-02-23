package com.hyeobjin.application.admin.service.item;

import com.hyeobjin.domain.repository.item.ItemRepository;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

@Slf4j
@Transactional
@DisplayName("관리자 제품 테스트")
@SpringBootTest
class AdminItemServiceTest {

    @Autowired
    private AdminItemService adminItemService;

    @Autowired
    private ItemRepository itemRepository;

    @Test
    @DisplayName("다중 제품 삭제")
    void deleteAllByIn() {

        // given
        List<Long> itemIds = new ArrayList<>();
        itemIds.add(8L);
        itemIds.add(9L);

        // when
        adminItemService.deleteItemIds(itemIds);

        // then
        boolean existById1 = itemRepository.existsById(8L);
        boolean existById2 = itemRepository.existsById(9L);

        assertThat(existById1).isFalse();
        assertThat(existById2).isFalse();
    }

}