package com.hyeobjin.application.common.dto.file;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class InquiryFileBoxDTO {

    private Long fileBoxId;
    private String fileName;
    private String fileOrgName;
    private Long fileSize;
    private String fileType;
    private String filePath;
    private Boolean isMain;
    private Boolean updated;
}
