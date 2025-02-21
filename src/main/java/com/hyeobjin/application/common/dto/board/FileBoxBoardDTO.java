package com.hyeobjin.application.common.dto.board;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class FileBoxBoardDTO {

    private Long fileBoxId;
    private String fileName;
    private String fileOrgName;
    private Long fileSize;
    private String fileType;
    private String filePath;

    private Long boardId;

    public FileBoxBoardDTO(Long boardId) {
        this.boardId = boardId;
    }
}
