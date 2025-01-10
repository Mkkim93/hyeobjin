package com.hyeobjin.domain.repository;

import com.hyeobjin.application.dto.item.FindByItemDTO;

public interface ItemRepositoryCustom {

    FindByItemDTO findByItem(String itemNum);
}
