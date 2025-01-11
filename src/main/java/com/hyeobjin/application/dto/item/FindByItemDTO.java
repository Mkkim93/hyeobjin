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
    private List<FindFileBoxDTO> fileBoxes;
    private String itemNum;

    @QueryProjection
    public FindByItemDTO(Long itemId, String itemName,
                         List<FindFileBoxDTO> fileBoxes, String itemNum) {
        this.itemId = itemId;
        this.itemName = itemName;
        this.fileBoxes = fileBoxes;
        this.itemNum = itemNum;
    }

    @Override
    public String toString() {
        return "FindByItemDTO{" +
                "itemId=" + itemId +
                ", itemName='" + itemName + '\'' +
                ", itemNum='" + itemNum + '\'' +
                ", fileBoxes=" + (fileBoxes != null ? fileBoxes.size() + " items" : "null") +
                '}';
    }
}
