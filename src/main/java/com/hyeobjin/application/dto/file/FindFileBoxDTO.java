package com.hyeobjin.application.dto.file;


import com.hyeobjin.domain.entity.FileBox;
import com.hyeobjin.domain.entity.Item;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class FindFileBoxDTO {

    private Long fileBoxId;
    private String fileName;
    private String fileOrgName;
    private Long fileSize;
    private String fileType;
    private String filePath;

    private Item item;

    public FindFileBoxDTO(FileBox fileBox) {
        this.fileBoxId = fileBox.getId();
        this.fileName = fileBox.getFileName();
        this.fileOrgName = fileBox.getFileOrgName();
        this.fileSize = fileBox.getFileSize();
        this.fileType = fileBox.getFileType();
        this.filePath = fileBox.getFilePath();
    }
}
