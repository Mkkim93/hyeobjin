package com.hyeobjin.domain.repository.item;

import com.hyeobjin.application.dto.item.FindByItemDTO;
import com.hyeobjin.application.dto.item.UpdateItemDTO;

public interface ItemRepositoryCustom {

    FindByItemDTO findByItem(Long manuId, Long itemId);

    UpdateItemDTO updateItem(UpdateItemDTO updateItemDTO);
}
