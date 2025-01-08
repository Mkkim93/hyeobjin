package com.hyeobjin.application.service.manufacturer;

import com.hyeobjin.application.dto.manu.ManufactureDTO;
import lombok.extern.slf4j.Slf4j;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@Slf4j
@SpringBootTest
class ManufacturerServiceTest {

    @Autowired ManufacturerService manufacturerService;

    @Test
    @DisplayName("findIdByManuName test")
    void findIdByManuName() {
        String manuName = "예림";
        Long idByManuName = manufacturerService.findIdByManuName(manuName);
        log.info("idByManuName={}", idByManuName);
        Assertions.assertThat(idByManuName).isEqualTo(2L);
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
}