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
    private String itemType; // 중요
    private LocalDateTime itemRegDate;
    private LocalDateTime itemUpdate;
    private String itemYN;
    private Long manuId;
    private String manuName;

    @QueryProjection
    public FindAdminItemDTO(Long itemId, String itemNum, String itemName,
                             LocalDateTime itemRegDate, LocalDateTime itemUpdate,
                            String itemYN, Long manuId, String manuName) {
        this.itemId = itemId;
        this.itemNum = itemNum;
        this.itemName = itemName;

        this.itemRegDate = itemRegDate;
        this.itemUpdate = itemUpdate;
        this.itemYN = itemYN;
        this.manuId = manuId;
        this.manuName = manuName;
    }
}
