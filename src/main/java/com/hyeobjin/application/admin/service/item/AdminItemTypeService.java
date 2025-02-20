package com.hyeobjin.application.admin.service.item;

import com.hyeobjin.application.admin.dto.item.type.AdminItemTypeDTO;
import com.hyeobjin.application.admin.dto.item.type.UpdateItemTypeDTO;
import com.hyeobjin.domain.entity.item.ItemType;
import com.hyeobjin.domain.repository.item.ItemTypeRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
@RequiredArgsConstructor
public class AdminItemTypeService {

    private final ItemTypeRepository itemTypeRepository;

    public void save(String newItemTypeName) {

        ItemType itemType = new ItemType(newItemTypeName);

        itemTypeRepository.save(itemType);
    }

    public List<AdminItemTypeDTO> findItemTypeList() {

        List<ItemType> itemTypeList = itemTypeRepository.findAll();

        return itemTypeList.stream().map(itemType -> new AdminItemTypeDTO(
                itemType.getId(),
                itemType.getTypeName()
        )).collect(Collectors.toList());
    }


    public void updateItemType(Long itemTypeId, UpdateItemTypeDTO updateItemTypeDTO) {

        ItemType itemType = itemTypeRepository.findById(
                        itemTypeId)
                .orElseThrow(() -> new EntityNotFoundException("해당 제품 타입이 존재 하지 않습니다."));

        itemType.updateTypeName(updateItemTypeDTO.getUpdateItemTypeName());
    }

    public void deleteItemType(Long itemTypeId) {

        if (!itemTypeRepository.existsById(itemTypeId)) {
            throw new EntityNotFoundException("삭제할 제품 타입이 존재하지 않습니다: " + itemTypeId);
        }

        itemTypeRepository.deleteById(itemTypeId);
    }

    public ItemType findById(Long itemTypeId) {
        return itemTypeRepository.findById(itemTypeId).orElseThrow(() -> new EntityNotFoundException("타입 조회 중 오류 발생"));
    }


}
