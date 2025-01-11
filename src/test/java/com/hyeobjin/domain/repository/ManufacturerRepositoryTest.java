package com.hyeobjin.domain.repository;

import com.hyeobjin.application.dto.manu.ManufactureDTO;
import com.hyeobjin.domain.entity.manufacturer.Manufacturer;
import com.hyeobjin.domain.repository.manu.ManufacturerRepository;
import lombok.extern.slf4j.Slf4j;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.Assertions.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@Slf4j
@SpringBootTest
class ManufacturerRepositoryTest {

    @Autowired
    ManufacturerRepository manufacturerRepository;

    @Test
    @DisplayName("save ManuName test")
    void save() {
        ManufactureDTO manufactureDTO = new ManufactureDTO();
        manufactureDTO.setManuName("예림");
        Manufacturer saved = manufacturerRepository.save(manufactureDTO.toEntity(manufactureDTO.getManuName()));
        log.info("before saved manuName={}", manufactureDTO.getManuName());
        log.info("after saved manuName={}", saved.getManuName());
        assertThat(manufactureDTO.getManuName()).isEqualTo(saved.getManuName());
    }

    @Test
    @DisplayName("findByIdForManuName test")
    void findIdByManuName() {
        String manuName = "KCC";
        Manufacturer manufacturerByManuName = manufacturerRepository.findManufacturerByManuName(manuName);

        log.info("findManuId={}", manufacturerByManuName.getId());

        assertThat(1L).isEqualTo(manufacturerByManuName.getId());
    }

    @Test
    @DisplayName("exist ManuName test")
    void existManuName() {
        ManufactureDTO manufactureDTO = new ManufactureDTO();
        manufactureDTO.setManuName("KCC"); // 이미 존재하는 제조사 등록 시 true

        Boolean exists = manufacturerRepository.existsByManuName(manufactureDTO.getManuName());
        log.info("exists={}", exists);
        assertThat(exists).isTrue();
    }

    @Test
    @DisplayName("update manuName test")
    void updateMenuName() {
        ManufactureDTO manufactureDTO = new ManufactureDTO();

        manufactureDTO.setManuId(1L);
        manufactureDTO.setManuName("KCG");

        Integer updateCount = manufacturerRepository.updateManuName(manufactureDTO.getManuId(), manufactureDTO.getManuName());

        assertThat(updateCount).isEqualTo(1);
    }

    @Test
    @DisplayName("delete manufacturer test (manuYN : N -> Y)")
    void deleteManufacturer() {
        ManufactureDTO manufactureDTO = new ManufactureDTO();
        manufactureDTO.setManuId(1L);

        Integer deleteCount = manufacturerRepository.deleteManufacturer(manufactureDTO.getManuId());

        assertThat(deleteCount).isEqualTo(1);
    }
}