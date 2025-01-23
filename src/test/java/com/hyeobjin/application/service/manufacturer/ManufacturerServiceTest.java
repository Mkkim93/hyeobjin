package com.hyeobjin.application.service.manufacturer;

import com.hyeobjin.application.dto.manu.ManufactureDTO;
import com.hyeobjin.domain.entity.manufacturer.Manufacturer;
import lombok.extern.slf4j.Slf4j;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

@Slf4j
@SpringBootTest
class ManufacturerServiceTest {

    @Autowired ManufacturerService manufacturerService;

    @Test
    @DisplayName("findIdByManuName test")
    void findIdByManuName() {
        String manuName = "예림";
        Manufacturer manufacturer = manufacturerService.findIdByManuName(manuName);
        log.info("manuId={}", manufacturer.getId());
        log.info("manuName={}", manufacturer.getManuName());
        assertThat(manufacturer.getId()).isEqualTo(2L);
    }

    @Test
    @DisplayName("save Manufacturer fail test")
    void saveFail() {
        ManufactureDTO manufactureDTO = new ManufactureDTO();
        manufactureDTO.setManuName("KCC");
        manufacturerService.saveManu(manufactureDTO); // 등록된 제조사 (중복)
    }

    @Test
    @DisplayName("save new Manufacturer success test")
    void saveSuccess() {
        ManufactureDTO manufactureDTO = new ManufactureDTO();
        manufactureDTO.setManuName("휴그린");
        manufacturerService.saveManu(manufactureDTO); // 새로운 제조사
    }

    @Test
    @DisplayName("findAll manufacturer test")
    void findAll() {
        manufacturerService.findAll().stream().forEach(System.out::println);
    }

    @Test
    @DisplayName("update manufacturer test")
    void updateManufacturer() {
        ManufactureDTO manufactureDTO = new ManufactureDTO();
        manufactureDTO.setManuId(3L);
        manufactureDTO.setManuName("휴그린");
//        manufactureDTO.setManuYN("Y");
        manufacturerService.update(manufactureDTO);
    }
}