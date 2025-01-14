package com.hyeobjin.application.dto.item;

import com.hyeobjin.application.dto.file.FindFileBoxDTO;
import com.querydsl.core.annotations.QueryProjection;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
public class FindByItemDTO {

    private Long itemId;
    private String itemName;
    private String itemNum;
    private String itemUse;
    private String itemSpec;
    private String itemDescription;
    private String itemType;
    private String manuName;
    private List<FindFileBoxDTO> fileBoxes;


    @QueryProjection
    public FindByItemDTO(Long itemId, String itemName, String itemNum, String itemUse,
                         String itemSpec, String itemDescription, String itemType,
                         String manuName, List<FindFileBoxDTO> fileBoxes) {
        this.itemId = itemId;
        this.itemName = itemName;
        this.itemNum = itemNum;
        this.itemUse = itemUse;
        this.itemSpec = itemSpec;
        this.itemDescription = itemDescription;
        this.itemType = itemType;
        this.manuName = manuName;
        this.fileBoxes = fileBoxes;
    }

    @Override
    public String toString() {
        return "FindByItemDTO{" +
                "itemId=" + itemId +
                ", itemName='" + itemName + '\'' +
                ", itemNum='" + itemNum + '\'' +
                ", itemUse='" + itemUse + '\'' +
                ", itemSpec='" + itemSpec + '\'' +
                ", itemDescription='" + itemDescription + '\'' +
                ", itemType='" + itemType + '\'' +
                ", fileBoxes=" + fileBoxes +
                '}';
    }
}
