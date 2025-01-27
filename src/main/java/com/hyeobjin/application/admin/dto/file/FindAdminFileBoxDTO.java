package com.hyeobjin.application.admin.dto.file;

import com.hyeobjin.domain.entity.file.FileBox;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class FindAdminFileBoxDTO {

    private Long fileBoxId;
    private String fileName;
    private String fileOrgName;
    private Long fileSize;
    private String fileType;
    private String filePath;
    private Boolean isMain;

    public FindAdminFileBoxDTO(FileBox fileBox) {
        this.fileBoxId = fileBox.getId();
        this.fileName = fileBox.getFileName();
        this.fileOrgName = fileBox.getFileOrgName();
        this.fileSize = fileBox.getFileSize();
        this.fileType = fileBox.getFileType();
        this.filePath = fileBox.getFilePath();
        this.isMain = fileBox.getIsMain();
    }
}
