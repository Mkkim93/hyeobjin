package com.hyeobjin.application.admin.dto.manu;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class FindManufacturerDTO {

    private Long manuId;
    private String manuName;
    private Long itemCount;
    private String manuYN;

    public FindManufacturerDTO(Long manuId, String manuName, Long itemCount, String manuYN) {
        this.manuId = manuId;
        this.manuName = manuName;
        this.itemCount = itemCount;
        this.manuYN = manuYN;
    }

    public FindManufacturerDTO(Long itemCount) {
        this.itemCount = itemCount;
    }
}
