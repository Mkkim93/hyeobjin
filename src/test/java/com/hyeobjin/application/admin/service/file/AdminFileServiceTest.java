package com.hyeobjin.application.admin.service.file;

import com.hyeobjin.domain.repository.file.FileBoxRepository;
import lombok.extern.slf4j.Slf4j;
import org.assertj.core.api.Assertions;
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
@DisplayName("관리자 파일 테스트")
@SpringBootTest
class AdminFileServiceTest {

    @Autowired
    private FileBoxRepository fileBoxRepository;

    @Autowired
    private AdminFileService adminFileService;

    @Test
    @DisplayName("여러개의 itemId 를 기준으로 해당 filebox 모든 데이터 영구 삭제")
    void findByItemIdsInDeleteFile() {

        // given
        List<Long> itemIds = new ArrayList<>();
        itemIds.add(1L);
        itemIds.add(3L);

        // when
        adminFileService.deleteFiles(itemIds);

        // then
        List<Long> deletedItemIds = fileBoxRepository.findFileBoxIdsByItemIdIn(itemIds);
        assertThat(deletedItemIds).isEmpty();
    }
}