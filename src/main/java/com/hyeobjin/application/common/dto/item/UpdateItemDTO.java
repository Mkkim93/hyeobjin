package com.hyeobjin.application.common.dto.item;

import com.hyeobjin.application.common.dto.board.FileBoxBoardDTO;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Data
@NoArgsConstructor
public class UpdateItemDTO {

    private Long itemId;
    private String itemNum;
    private String itemName;
    private String itemUse;
    private String itemSpec;
    private String itemInColor;
    private String itemOutColor;
    private String itemFrameWidth;
    private String itemType;
    private String itemDescription;
    private String itemYN;
    private LocalDateTime itemUpdate;

    private Boolean isMain;

    private Long manuId;
    private String manuName;

    private List<FileBoxBoardDTO> updateFiles;

    private Long fileBoxId; // 추가 01/30

    public UpdateItemDTO(Long itemId, String itemNum, String itemName, Boolean isMain,
                         String itemUse, String itemSpec, String itemInColor, String itemOutColor, String itemFrameWidth,
                         String itemType, String itemDescription,
                         String itemYN, LocalDateTime itemUpdate,
                         Long manuId, String manuName, Long fileBoxId) {
        this.itemId = itemId;
        this.itemNum = itemNum;
        this.itemName = itemName;
        this.isMain = isMain;
        this.itemUse = itemUse;
        this.itemSpec = itemSpec;
        this.itemInColor = itemInColor;
        this.itemOutColor = itemOutColor;
        this.itemFrameWidth = itemFrameWidth;
        this.itemType = itemType;
        this.itemDescription = itemDescription;
        this.itemYN = itemYN;
        this.itemUpdate = itemUpdate;
        this.manuId = manuId;
        this.manuName = manuName;
        this.fileBoxId = fileBoxId;
    }
}
