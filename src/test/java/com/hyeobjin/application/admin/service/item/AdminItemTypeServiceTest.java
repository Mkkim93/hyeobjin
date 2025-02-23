package com.hyeobjin.application.admin.service.item;

import com.hyeobjin.application.admin.dto.item.type.AdminItemTypeDTO;
import com.hyeobjin.application.admin.dto.item.type.UpdateItemTypeDTO;
import com.hyeobjin.domain.entity.item.ItemType;
import com.hyeobjin.domain.repository.item.ItemTypeRepository;
import lombok.extern.slf4j.Slf4j;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.*;

@Slf4j
@Transactional
@DisplayName("관리자 제품 등록 테스트")
@SpringBootTest
class AdminItemTypeServiceTest {

    @Autowired
    AdminItemTypeService adminItemTypeService;

    @Autowired
    ItemTypeRepository itemTypeRepository;

    @Test
    @DisplayName("제품 타입 등록")
    void saveType() {

        // given
        AdminItemTypeDTO adminItemTypeDTO = new AdminItemTypeDTO();
        adminItemTypeDTO.setItemTypeName("단창");

        // when
        adminItemTypeService.save(adminItemTypeDTO.getItemTypeName());

        // then
        ItemType itemType = itemTypeRepository.findById(1L).get();
        assertThat(adminItemTypeDTO.getItemTypeName()).isEqualTo(itemType.getTypeName());
    }

    @Test
    @DisplayName("제품 타입 조회")
    void findType() {

        // when
        List<AdminItemTypeDTO> itemTypeList = adminItemTypeService.findItemTypeList();

        // then
        itemTypeList.stream().forEach(System.out::println);

        // when
        Assertions.assertThat(itemTypeList).isNotEmpty();
    }

    @Test
    @DisplayName("제품 타입 수정")
    void updateType() {

        // given
        UpdateItemTypeDTO updateItemTypeDTO = new UpdateItemTypeDTO();
        updateItemTypeDTO.setItemTypeId(1L);
        updateItemTypeDTO.setItemTypeName("단창");

        // when
        adminItemTypeService.updateItemType(updateItemTypeDTO);
        ItemType itemType = itemTypeRepository.findById(updateItemTypeDTO.getItemTypeId()).get();

        // then
        assertThat(itemType.getTypeName()).isEqualTo(updateItemTypeDTO.getItemTypeName());
    }

    @Test
    @DisplayName("제품 타입 삭제")
    void deletedType() {

        // given
        List<Long> deletedIds = new ArrayList<>();
        deletedIds.add(1L);
        deletedIds.add(2L);

        // when
        adminItemTypeService.deleteItemType(deletedIds);

        Optional<ItemType> deleteById = itemTypeRepository.findById(1L);

        // then
        assertThat(deleteById).isEmpty();
    }
}