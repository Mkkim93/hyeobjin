package com.hyeobjin.application.admin.service.file;

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
class AdminFileServiceTest {


    @Autowired
    AdminFileService adminFileService;

    @Test
    @DisplayName("여러개의 itemId 를 기준으로 해당 filebox 모든 데이터 영구 삭제")
    void findByItemIdsInDeleteFile() {
        List<Long> itemIds = new ArrayList<>();

        itemIds.add(1L);
        itemIds.add(3L);
        adminFileService.deleteFiles(itemIds);
    }
}