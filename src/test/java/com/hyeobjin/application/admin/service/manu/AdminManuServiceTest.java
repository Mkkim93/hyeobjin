package com.hyeobjin.application.admin.service.manu;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.hyeobjin.application.admin.dto.manu.FindManufacturerDTO;
import com.hyeobjin.domain.entity.manufacturer.Manufacturer;
import com.hyeobjin.domain.repository.item.ItemRepository;
import com.hyeobjin.domain.repository.manu.ManufacturerRepository;
import lombok.extern.slf4j.Slf4j;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

@Slf4j
@SpringBootTest
@Transactional
class AdminManuServiceTest {

    @Autowired
    private AdminManuService adminManuService;

    @Autowired
    private ManufacturerRepository manufacturerRepository;

    @Autowired
    private ItemRepository itemRepository;

    @Test
    @DisplayName("삭제 : 해당 제조사에 모든 제품과 파일데이터 영구 삭제")
    void deleteManufacturer() {

        // given
        Long manuId = 3L;

        // when
        adminManuService.delete(manuId);

        // then
        boolean manuExist = manufacturerRepository.existsById(manuId);
        assertThat(manuExist).isFalse();
    }

}