package com.hyeobjin.application.common.dto.item;

import com.fasterxml.jackson.annotation.JsonView;
import com.hyeobjin.application.common.dto.file.FindFileBoxDTO;
import com.querydsl.core.annotations.QueryProjection;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Getter
@NoArgsConstructor
public class FindByItemDTO {

    public interface SummaryView {}

    @JsonView(SummaryView.class)
    private Long itemId;

    private String itemName;

    @JsonView(SummaryView.class)
    private String itemNum;

    private String itemUse;
    private String itemSpec;
    private String itemInColor;
    private String itemOutColor;
    private String itemFrameWidth;
    private String itemDescription;
    private String itemType;

    @JsonView(SummaryView.class)
    private Long manuId;
    private String manuName;
    private List<FindFileBoxDTO> fileBoxes;


    @QueryProjection
    public FindByItemDTO(Long itemId, String itemName, String itemNum, String itemUse,
                         String itemSpec, String itemInColor, String itemOutColor, String itemFrameWidth,
                         String itemDescription, String itemType, Long manuId,
                         String manuName, List<FindFileBoxDTO> fileBoxes) {
        this.itemId = itemId;
        this.itemName = itemName;
        this.itemNum = itemNum;
        this.itemUse = itemUse;
        this.itemSpec = itemSpec;
        this.itemInColor = itemInColor;
        this.itemOutColor = itemOutColor;
        this.itemFrameWidth = itemFrameWidth;
        this.itemDescription = itemDescription;
        this.itemType = itemType;
        this.manuId = manuId;
        this.manuName = manuName;
        this.fileBoxes = fileBoxes;
    }

    public FindByItemDTO(Long itemId, String itemNum, Long manuId) {
        this.itemId = itemId;
        this.itemNum = itemNum;
        this.manuId = manuId;
    }

    public FindByItemDTO(Long manuId, Long itemId) {
        this.manuId = manuId;
        this.itemId = itemId;
    }

    public FindByItemDTO(Long itemId) {
        this.itemId = itemId;
    }
}
