package com.hyeobjin.application.admin.dto.file;

import com.querydsl.core.annotations.QueryProjection;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
public class AdminInquiryFileDTO {
    private Long fileBoxId;
    private String fileOrgName;
    private String filePath;
    private String fileName;

    @QueryProjection
    public AdminInquiryFileDTO(Long fileBoxId, String filePath, String fileOrgName, String fileName) {
        this.fileBoxId = fileBoxId;
        this.filePath = filePath;
        this.fileOrgName = fileOrgName;
        this.fileName = fileName;
    }
}
