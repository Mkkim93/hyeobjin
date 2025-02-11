package com.hyeobjin.application.common.dto.item.type;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class FindItemTypeDTO {

    private Long itemTypeId;
    private String itemTypeName;
    private Long itemId;
    private String itemName;

    public FindItemTypeDTO(Long itemTypeId, String itemTypeName, Long itemId, String itemName) {
        this.itemTypeId = itemTypeId;
        this.itemTypeName = itemTypeName;
        this.itemId = itemId;
        this.itemName = itemName;
    }



}
