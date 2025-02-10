package com.hyeobjin.application.admin.dto.item;

import com.hyeobjin.application.common.dto.file.FileBoxItemDTO;
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
    private String itemInColor;
    private String itemOutColor;
    private String itemFrameWidth;
    private String itemDescription;
    private String itemYN;
    private LocalDateTime itemUpdate;
    private String fileDeleted;

    private Boolean isMain;
    private String freeContent;

    private Long manuId;
    private String manuName;

    private Long glassSpecId;
    private Long itemTypeId;

    private Long fileBoxId;

    private Boolean updated;

    private FileBoxItemDTO updateMainFile;

    private List<FileBoxItemDTO> updateSubFiles;

    private List<Long> updatedFileIds;

    public UpdateItemDTO(Long itemId, String itemNum, String itemName, Boolean isMain,
                         String itemUse,  String itemInColor, String itemOutColor, String itemFrameWidth,
                          String itemDescription,
                         String itemYN, LocalDateTime itemUpdate, String freeContent,
                         Long manuId, String manuName, Long itemTypeId, Long glassSpecId) {
        this.itemId = itemId;
        this.itemNum = itemNum;
        this.itemName = itemName;
        this.isMain = isMain;
        this.itemUse = itemUse;
        this.itemInColor = itemInColor;
        this.itemOutColor = itemOutColor;
        this.itemFrameWidth = itemFrameWidth;
        this.itemDescription = itemDescription;
        this.itemYN = itemYN;
        this.itemUpdate = itemUpdate;
        this.freeContent = freeContent;
        this.manuId = manuId;
        this.manuName = manuName;
        this.itemTypeId = itemTypeId;
        this.glassSpecId = glassSpecId;
    }
}
