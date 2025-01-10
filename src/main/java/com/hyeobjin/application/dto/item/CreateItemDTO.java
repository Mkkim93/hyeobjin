package com.hyeobjin.application.dto.item;

import com.hyeobjin.application.dto.file.FileBoxItemDTO;
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

    private Long itemId;
    private String itemNum;
    private String itemName;
    private String itemUse;
    private String itemSpec;
    private String itemDescription;
    private String itemType;
    private String itemYN;

    private String menuName;
    private Long manuId;

    private List<FileBoxItemDTO> itemFiles;

    public Item toEntity(CreateItemDTO createItemDTO) {

        return Item.builder()
                .itemId(createItemDTO.getItemId())
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
