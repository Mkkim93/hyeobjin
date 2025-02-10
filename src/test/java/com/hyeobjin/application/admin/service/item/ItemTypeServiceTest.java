package com.hyeobjin.application.admin.service.item;

import com.hyeobjin.application.admin.dto.item.type.ItemTypeDTO;
import com.hyeobjin.application.admin.dto.item.type.UpdateItemTypeDTO;
import com.hyeobjin.domain.entity.item.ItemType;
import com.hyeobjin.domain.repository.item.ItemTypeRepository;
import lombok.extern.slf4j.Slf4j;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;
import java.util.Optional;

@Slf4j
@SpringBootTest
class ItemTypeServiceTest {

    @Autowired
    ItemTypeService itemTypeService;

    @Autowired
    ItemTypeRepository itemTypeRepository;

    @Test
    @DisplayName("제품 타입 등록")
    void saveType() {

        ItemTypeDTO itemTypeDTO = new ItemTypeDTO();
        itemTypeDTO.setItemTypeName("단창");

        itemTypeService.save(itemTypeDTO.getItemTypeName());

        ItemType itemType = itemTypeRepository.findById(1L).get();

        Assertions.assertThat(itemTypeDTO.getItemTypeName()).isEqualTo(itemType.getTypeName());
    }

    @Test
    @DisplayName("제품 타입 조회")
    void findType() {

        List<ItemTypeDTO> itemTypeList = itemTypeService.findItemTypeList();

        itemTypeList.stream().forEach(System.out::println);

    }

    @Test
    @DisplayName("제품 타입 수정")
    void updateType() {

        UpdateItemTypeDTO updateItemTypeDTO = new UpdateItemTypeDTO();
        updateItemTypeDTO.setItemTypeId(1L);
        updateItemTypeDTO.setUpdateItemTypeName("단창");

        itemTypeService.updateItemType(updateItemTypeDTO.getItemTypeId(), updateItemTypeDTO);

        ItemType itemType = itemTypeRepository.findById(updateItemTypeDTO.getItemTypeId()).get();

        Assertions.assertThat(itemType.getTypeName()).isEqualTo(updateItemTypeDTO.getUpdateItemTypeName());
    }

    @Test
    @DisplayName("제품 타입 삭제")
    void deletedType() {

        Long deleteId = 1L;

        itemTypeService.deleteItemType(deleteId);

        Optional<ItemType> deleteById = itemTypeRepository.findById(1L);

        Assertions.assertThat(deleteById).isEmpty();
    }
}