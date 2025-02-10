package com.hyeobjin.application.admin.dto.item.glass;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UpdateGlassSpecDTO {

    private Long glassSpecId;
    private String updateGlassSize;
}
