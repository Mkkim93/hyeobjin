package com.hyeobjin.application.common.dto.item;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class FindItemNameDTO {

    private Long itemId;
    private String itemName;

    public FindItemNameDTO(Long itemId, String itemName) {
        this.itemId = itemId;
        this.itemName = itemName;
    }
}
