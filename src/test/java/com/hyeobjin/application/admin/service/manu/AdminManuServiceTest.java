package com.hyeobjin.application.admin.service.manu;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.hyeobjin.application.admin.dto.manu.FindManufacturerDTO;
import com.hyeobjin.domain.entity.manufacturer.Manufacturer;
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
class AdminManuServiceTest {

    @Autowired
    private AdminManuService adminManuService;

    @Test
    @DisplayName("제조사 삭제 (해당 제조사에 모든 제품과 파일데이터 영구 삭제)")
    void deleteManufacturer() {
        adminManuService.delete(3L);
    }

}