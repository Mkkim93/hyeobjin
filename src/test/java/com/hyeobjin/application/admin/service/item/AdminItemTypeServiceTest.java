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

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Slf4j
@SpringBootTest
class AdminItemTypeServiceTest {

    @Autowired
    AdminItemTypeService adminItemTypeService;

    @Autowired
    ItemTypeRepository itemTypeRepository;

    @Test
    @DisplayName("제품 타입 등록")
    void saveType() {

        AdminItemTypeDTO adminItemTypeDTO = new AdminItemTypeDTO();
        adminItemTypeDTO.setItemTypeName("단창");

        adminItemTypeService.save(adminItemTypeDTO.getItemTypeName());

        ItemType itemType = itemTypeRepository.findById(1L).get();

        Assertions.assertThat(adminItemTypeDTO.getItemTypeName()).isEqualTo(itemType.getTypeName());
    }

    @Test
    @DisplayName("제품 타입 조회")
    void findType() {

        List<AdminItemTypeDTO> itemTypeList = adminItemTypeService.findItemTypeList();

        itemTypeList.stream().forEach(System.out::println);

    }

    @Test
    @DisplayName("제품 타입 수정")
    void updateType() {

        UpdateItemTypeDTO updateItemTypeDTO = new UpdateItemTypeDTO();
        updateItemTypeDTO.setItemTypeId(1L);
        updateItemTypeDTO.setItemTypeName("단창");

        adminItemTypeService.updateItemType(updateItemTypeDTO);

        ItemType itemType = itemTypeRepository.findById(updateItemTypeDTO.getItemTypeId()).get();

        Assertions.assertThat(itemType.getTypeName()).isEqualTo(updateItemTypeDTO.getItemTypeName());
    }

    @Test
    @DisplayName("제품 타입 삭제")
    void deletedType() {

        List<Long> deletedIds = new ArrayList<>();
        deletedIds.add(1L);
        deletedIds.add(2L);

        adminItemTypeService.deleteItemType(deletedIds);

        Optional<ItemType> deleteById = itemTypeRepository.findById(1L);

        Assertions.assertThat(deleteById).isEmpty();
    }
}