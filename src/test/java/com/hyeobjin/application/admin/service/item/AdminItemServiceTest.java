package com.hyeobjin.application.admin.service.item;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@Slf4j
@SpringBootTest
class AdminItemServiceTest {

    @Autowired
    private AdminItemService adminItemService;

    @Test
    @DisplayName("다중 제품 삭제")
    void deleteAllByIn() {
        List<Long> itemIds = new ArrayList<>();
        itemIds.add(8L);
        itemIds.add(9L);
        adminItemService.deleteItemIds(itemIds);
    }

}