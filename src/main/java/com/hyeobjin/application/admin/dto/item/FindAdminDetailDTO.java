package com.hyeobjin.application.admin.dto.item;

import com.hyeobjin.application.admin.dto.file.FindAdminFileBoxDTO;
import com.querydsl.core.annotations.QueryProjection;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Data
@NoArgsConstructor
public class FindAdminDetailDTO {

    private Long itemId;
    private String itemName;
    private String itemNum;
    private String itemInColor;
    private String itemOutColor;
    private String itemFrameWidth;
    private String itemDescription;
    private LocalDateTime itemRegDate;
    private LocalDateTime itemUpdate;
    private String itemUse;
    private String itemYN;
    private Long manuId;
    private String manuName;
    private String freeContent;
    private String itemType;
    private String glassSize;
    private List<FindAdminFileBoxDTO> fileBoxes;

    @QueryProjection
    public FindAdminDetailDTO(Long itemId,
                              String itemName,
                              String itemNum,
                              String itemUse,
                              String itemInColor,
                              String itemOutColor,
                              String itemFrameWidth,
                              String itemDescription,
                              LocalDateTime itemRegDate,
                              LocalDateTime itemUpdate,
                              String itemYN,
                              String freeContent,
                              Long manuId,
                              String manuName,
                              String itemType,
                              String glassSize,
                              List<FindAdminFileBoxDTO> fileBoxes) {
        this.itemId = itemId;
        this.itemName = itemName;
        this.itemNum = itemNum;
        this.itemUse = itemUse;
        this.itemInColor = itemInColor;
        this.itemOutColor = itemOutColor;
        this.itemFrameWidth = itemFrameWidth;
        this.itemDescription = itemDescription;
        this.itemRegDate = itemRegDate;
        this.itemUpdate = itemUpdate;
        this.itemYN = itemYN;
        this.freeContent = freeContent;
        this.manuId = manuId;
        this.manuName = manuName;
        this.itemType = itemType;
        this.glassSize = glassSize;
        this.fileBoxes = fileBoxes;
    }
}
