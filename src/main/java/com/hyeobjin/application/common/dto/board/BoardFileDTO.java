package com.hyeobjin.application.common.dto.board;

import com.querydsl.core.annotations.QueryProjection;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

/**
 * 게시글 파일 등록 DTO
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class BoardFileDTO {

    private Long fileBoxId;
    private String fileName;
    private String fileOrgName;
    private Long fileSize; // TODO 지워도 될듯
    private String fileType;
    private String filePath;
    private LocalDateTime fileRegDate;

    private Long boardId;
    private Long userId;

    @QueryProjection
    public BoardFileDTO(Long fileBoxId, String fileOrgName,
                        String fileType, String filePath,
                        LocalDateTime fileRegDate, Long boardId) {
        this.fileBoxId = fileBoxId;
        this.fileName = fileOrgName;
        this.fileType = fileType;
        this.filePath = filePath;
        this.fileRegDate = fileRegDate;
        this.boardId = boardId;
    }
}
