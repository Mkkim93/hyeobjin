package com.hyeobjin.application.admin.dto.item.type;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UpdateItemTypeDTO {

    private Long itemTypeId;
    private String updateItemTypeName;
}
