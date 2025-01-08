package com.hyeobjin.application.dto.manu;

import com.hyeobjin.domain.entity.Manufacturer;
import lombok.Data;

@Data
public class ManufactureDTO {

    private Long manuId;
    private String manuName;

    public Manufacturer toEntity(String manuName) {
        return Manufacturer.builder()
                .manuName(manuName)
                .build();
    }

    public Long findByIdToDTO(Manufacturer manufacturer) {
        return manuId = manufacturer.getId();
    }
}
