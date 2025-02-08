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

    private Boolean updated;

    private FileBoxItemDTO updateMainFile;

    private List<FileBoxItemDTO> updateSubFiles;

    public List<String> getUpdateSubFileNames() {
        List<String> updateNames = null;
        for (FileBoxItemDTO updateSubFile : updateSubFiles) {
            updateNames.add(updateSubFile.getFileName());
        }
        return  updateNames;
    }

    public UpdateItemDTO(Long itemId, String itemNum, String itemName, Boolean isMain,
                         String itemUse, String itemSpec, String itemInColor, String itemOutColor, String itemFrameWidth,
                         String itemType, String itemDescription,
                         String itemYN, LocalDateTime itemUpdate,
                         Long manuId, String manuName) {
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
    }
}
