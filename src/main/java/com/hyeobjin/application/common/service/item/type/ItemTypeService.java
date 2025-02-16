package com.hyeobjin.application.common.service.item.type;

import com.hyeobjin.application.common.dto.item.type.FindItemTypeDTO;
import com.hyeobjin.application.common.dto.item.type.ItemTypeDTO;
import com.hyeobjin.domain.repository.item.ItemTypeRepository;
import com.hyeobjin.domain.repository.item.type.ItemTypeQueryRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class ItemTypeService {

    private final ItemTypeRepository itemTypeRepository;
    private final ItemTypeQueryRepository itemTypeQueryRepository;

    public List<ItemTypeDTO> findAll(Long manuId) {
       return itemTypeQueryRepository.findByCategoryByManuId(manuId);
    }

    public List<FindItemTypeDTO> itemTypeSelectList(Long itemTypeId, Long manuId) {

        return itemTypeRepository.findByItemNameList(itemTypeId, manuId);
    }
}
