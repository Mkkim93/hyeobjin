package com.hyeobjin.application.common.dto.manu;

import com.hyeobjin.domain.entity.manufacturer.Manufacturer;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ManufactureDTO {

    private Long manuId;

    @NotNull(message = "제조사명은 필수입니다.")
    private String manuName;

    private String manuYN;

    public Manufacturer toEntity(String manuName) {
        return Manufacturer.builder()
                .manuName(manuName)
                .build();
    }

    public Long findByIdToDTO(Manufacturer manufacturer) {
        return manuId = manufacturer.getId();
    }

    public Manufacturer toEntity(ManufactureDTO manufactureDTO) {
        return Manufacturer.builder()
                .manuId(manufactureDTO.getManuId())
                .manuName(manufactureDTO.getManuName())
                .manuYN(manufactureDTO.getManuYN())
                .build();
    }
}
