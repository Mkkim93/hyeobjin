package com.hyeobjin.application.dto.item;

import com.hyeobjin.domain.entity.FileBox;
import com.hyeobjin.domain.entity.Item;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * item (제품) 등록을 위한 dto
 * item entity : 이름, 용도, 사양, 상세설명
 * file entity : 파일이름
 */
@Data
@NoArgsConstructor
public class CreateItemDTO {

    private String itemNum;
    private String itemName;
    private String itemUse;
    private String itemSpec;
    private String itemDescription;
    private String itemType;
    private Boolean itemYN;

    private String menuName;
    private Long manuId;

    private List<FileBox> itemFiles;

    public Item toEntity(CreateItemDTO createItemDTO) {
        return Item.builder()
                .itemNum(createItemDTO.getItemNum())
                .itemName(createItemDTO.getItemName())
                .itemUse(createItemDTO.getItemUse())
                .itemSpec(createItemDTO.getItemSpec())
                .itemType(createItemDTO.getItemType())
                .itemDescription(createItemDTO.getItemDescription())
                .itemYN(createItemDTO.getItemYN())
                .build();
    }
}
