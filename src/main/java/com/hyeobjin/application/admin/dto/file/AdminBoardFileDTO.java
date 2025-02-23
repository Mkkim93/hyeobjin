package com.hyeobjin.application.admin.dto.file;

import com.querydsl.core.annotations.QueryProjection;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AdminBoardFileDTO {

    private Long fileBoxId;
    private String fileName;
    private String fileOrgName;
    private Long fileSize;
    private String fileType;
    private String filePath;
    private LocalDateTime fileRegDate;

    private Long boardId;
    private Long userId;

    @QueryProjection
    public AdminBoardFileDTO(Long fileBoxId, String fileName, String fileOrgName,
                             Long fileSize, String fileType, String filePath,
                             LocalDateTime fileRegDate, Long boardId) {
        this.fileBoxId = fileBoxId;
        this.fileName = fileName;
        this.fileOrgName = fileOrgName;
        this.fileSize = fileSize;
        this.fileType = fileType;
        this.filePath = filePath;
        this.fileRegDate = fileRegDate;
        this.boardId = boardId;
    }
}
