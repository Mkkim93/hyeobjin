package com.hyeobjin.domain.repository.manu;

import com.hyeobjin.application.common.dto.manu.ManufactureDTO;
import com.hyeobjin.domain.entity.manufacturer.Manufacturer;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@Slf4j
@DataJpaTest
@DisplayName("제조사 테스트")
@Transactional
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class ManufacturerRepositoryTest {

    @Autowired
    private ManufacturerRepository manufacturerRepository;

    @Test
    @DisplayName("등록 : 제조사 등록")
    void save() {

        // given
        ManufactureDTO manufactureDTO = new ManufactureDTO();
        manufactureDTO.setManuName("예림");

        // when
        Manufacturer saved = manufacturerRepository.save(manufactureDTO.toEntity(manufactureDTO.getManuName()));

        // then
        log.info("before saved manuName={}", manufactureDTO.getManuName());
        log.info("after saved manuName={}", saved.getManuName());
        assertThat(manufactureDTO.getManuName()).isEqualTo(saved.getManuName());
    }

    @Test
    @DisplayName("조회 : 제조사명으로 해당 객체 조회")
    void findIdByManuName() {

        // given
        String manuName = "KCC";

        // when
        Manufacturer manufacturerByManuName = manufacturerRepository.findManufacturerByManuName(manuName);

        // then
        log.info("findManuId={}", manufacturerByManuName.getId());
        assertThat(1L).isEqualTo(manufacturerByManuName.getId());
    }

    @Test
    @DisplayName("조회 : 제조사 등록 여부 확인")
    void existManuName() {

        // given
        ManufactureDTO manufactureDTO = new ManufactureDTO();
        manufactureDTO.setManuName("KCC"); // 이미 존재하는 제조사 등록 시 true

        // when
        Boolean exists = manufacturerRepository.existsByManuName(manufactureDTO.getManuName());

        // then
        log.info("exists={}", exists);
        assertThat(exists).isTrue();
    }

    @Test
    @DisplayName("수정 : 제조사명 수정")
    void updateMenuName() {

        // given
        ManufactureDTO manufactureDTO = new ManufactureDTO();

        manufactureDTO.setManuId(1L);
        manufactureDTO.setManuName("KCG");

        // when
        Integer updateCount = manufacturerRepository.updateManuName(manufactureDTO.getManuId(), manufactureDTO.getManuName());

        // then
        assertThat(updateCount).isEqualTo(1);
    }

    @Test
    @DisplayName("수정 : 제조사 등록상태 변경")
    void deleteManufacturer() {

        // given
        ManufactureDTO manufactureDTO = new ManufactureDTO();
        manufactureDTO.setManuId(1L);

        // when
        Integer deleteCount = manufacturerRepository.deleteManufacturer(manufactureDTO.getManuId());

        // then
        assertThat(deleteCount).isEqualTo(1);
    }

    @Test
    @DisplayName("조회 : 제조사에 등록된 제품 수량 조회")
    void findByItemIds() {

        // given
        Long manuId = 1L;

        // when
        List<Long> result = manufacturerRepository.findByItemIds(manuId);

        // then
        assertThat(result.size()).isEqualTo(8);
        System.out.println("byItemIds = " + result);
    }
}