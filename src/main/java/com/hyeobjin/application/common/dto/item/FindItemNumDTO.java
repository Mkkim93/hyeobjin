package com.hyeobjin.application.common.dto.item;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class FindItemNumDTO {

    private Long itemId;
    private String itemNum;

    public FindItemNumDTO(Long itemId, String itemNum) {
        this.itemId = itemId;
        this.itemNum = itemNum;
    }
}
