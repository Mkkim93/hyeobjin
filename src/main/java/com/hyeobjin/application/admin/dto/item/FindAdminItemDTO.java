package com.hyeobjin.application.admin.dto.item;

import com.querydsl.core.annotations.QueryProjection;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
public class FindAdminItemDTO {

    private Long itemId;
    private String itemName;
    private String itemNum;
    private LocalDateTime itemRegDate;
    private LocalDateTime itemUpdate;
    private Boolean itemYN;
    private String itemType;
    private Long manuId;
    private String manuName;

    @QueryProjection
    public FindAdminItemDTO(Long itemId, String itemNum, String itemName,
                             LocalDateTime itemRegDate, LocalDateTime itemUpdate,
                            Boolean itemYN, String itemType, Long manuId, String manuName) {
        this.itemId = itemId;
        this.itemNum = itemNum;
        this.itemName = itemName;
        this.itemRegDate = itemRegDate;
        this.itemUpdate = itemUpdate;
        this.itemYN = itemYN;
        this.itemType = itemType;
        this.manuId = manuId;
        this.manuName = manuName;
    }
}
