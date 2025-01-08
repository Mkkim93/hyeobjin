package com.hyeobjin.domain.repository;

import com.hyeobjin.application.dto.manu.ManufactureDTO;
import com.hyeobjin.domain.entity.Manufacturer;
import lombok.extern.slf4j.Slf4j;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@Slf4j
@SpringBootTest
class ManufacturerRepositoryTest {

    @Autowired ManufacturerRepository manufacturerRepository;

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
        Manufacturer resultManuNameEntity = manufacturerRepository.findManufacturerByManuName(manuName);

        log.info("findManuName={}", resultManuNameEntity.getManuName());
        log.info("findManuId={}", resultManuNameEntity.getId());

        assertThat(manuName).isEqualTo(resultManuNameEntity.getManuName());
        assertThat(1L).isEqualTo(resultManuNameEntity.getId());
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
}