package com.hyeobjin.application.dto.item;

import com.hyeobjin.application.dto.board.FileBoxBoardDTO;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
public class UpdateItemDTO {

    private Long itemId;
    private String itemNum;
    private String itemName;
    private String itemUse;
    private String itemSpec;
    private String itemType;
    private String itemDescription;
    private String itemYN;

    private Long manuId;
    private String manuName;

    private List<FileBoxBoardDTO> updateFiles;

    public UpdateItemDTO(Long itemId, String itemNum, String itemName,
                         String itemUse, String itemSpec,
                         String itemType, String itemDescription,
                         String itemYN, Long manuId, String manuName) {
        this.itemId = itemId;
        this.itemNum = itemNum;
        this.itemName = itemName;
        this.itemUse = itemUse;
        this.itemSpec = itemSpec;
        this.itemType = itemType;
        this.itemDescription = itemDescription;
        this.itemYN = itemYN;
        this.manuId = manuId;
        this.manuName = manuName;

    }
}
