package com.hyeobjin.domain.repository.item;

import com.hyeobjin.application.admin.dto.item.FindAdminDetailDTO;
import com.hyeobjin.application.admin.dto.item.FindAdminItemDTO;
import com.hyeobjin.application.common.dto.item.FindByItemDTO;
import com.hyeobjin.application.common.dto.item.UpdateItemDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ItemRepositoryCustom {

    FindByItemDTO findByItem(Long manuId, Long itemId);

    UpdateItemDTO updateItem(UpdateItemDTO updateItemDTO);

    Page<FindAdminItemDTO> findItemList(Pageable pageable, String manuName);

    FindAdminDetailDTO findItemDetail(Long manuId, Long itemId);
}
