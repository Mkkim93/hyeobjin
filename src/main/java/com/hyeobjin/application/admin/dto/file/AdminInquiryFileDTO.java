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

    @QueryProjection
    public AdminInquiryFileDTO(Long fileBoxId, String filePath, String fileOrgName) {
        this.fileBoxId = fileBoxId;
        this.filePath = filePath;
        this.fileOrgName = fileOrgName;
    }
}
