package com.hyeobjin.application.admin.dto.manu;

import com.hyeobjin.domain.entity.manufacturer.Manufacturer;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SelectManufacturerListDTO {

    private Long manuId;
    private String manuName;
    private String manuYN;

    public Manufacturer toEntity(String manuName) {
        return Manufacturer.builder()
                .manuName(manuName)
                .build();
    }
}
