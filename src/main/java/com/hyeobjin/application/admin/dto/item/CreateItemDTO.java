package com.hyeobjin.application.admin.dto.item;

import com.hyeobjin.application.common.dto.file.FileBoxItemDTO;
import com.hyeobjin.domain.entity.item.GlassSpec;
import com.hyeobjin.domain.entity.item.Item;
import com.hyeobjin.domain.entity.item.ItemType;
import com.hyeobjin.domain.entity.manufacturer.Manufacturer;
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
    private String itemInColor;
    private String itemOutColor;
    private String itemFrameWidth;
    private String itemDescription;
    private Boolean itemYN;

    private Long glassSpecId;
    private Long itemTypeId;

    private String freeContent;

    private String manuName;
    private Long manuId;

    private List<FileBoxItemDTO> itemFiles;

    private Boolean isMain;

    public Item toEntity(CreateItemDTO createItemDTO,
                         GlassSpec glassSpec,
                         ItemType itemType,
                         Manufacturer manufacturer) {

        return Item.builder()
                .itemId(createItemDTO.getItemId())
                .itemNum(createItemDTO.getItemNum())
                .itemName(createItemDTO.getItemName())
                .itemUse(createItemDTO.getItemUse())
                .itemInColor(createItemDTO.getItemInColor())
                .itemOutColor(createItemDTO.getItemOutColor())
                .itemFrameWidth(createItemDTO.getItemFrameWidth())
                .itemDescription(createItemDTO.getItemDescription())
                .itemYN(createItemDTO.getItemYN())
                .freeContent(createItemDTO.getFreeContent())
                .glassSpec(glassSpec)
                .itemType(itemType)
                .manufacturer(manufacturer)
                .build();
    }
}
