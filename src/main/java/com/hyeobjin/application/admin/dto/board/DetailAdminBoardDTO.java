package com.hyeobjin.application.admin.dto.board;

import com.hyeobjin.application.admin.dto.file.AdminBoardFileDTO;
import com.querydsl.core.annotations.QueryProjection;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Data
@NoArgsConstructor
public class DetailAdminBoardDTO {

    private Long boardId;
    private String boardTitle;
    private String boardContent;
    private Long boardViewCount;
    private LocalDateTime boardRegDate;
    private LocalDateTime boardUpdate;
    private String boardType;
    private String boardYN;
    private String writer; // Users = name

    private List<AdminBoardFileDTO> adminBoardFiles;

    @QueryProjection
    public DetailAdminBoardDTO(Long boardId, String boardTitle,
                               String boardContent, Long boardViewCount, LocalDateTime boardRegDate,
                               LocalDateTime boardUpdate, String boardType, String boardYN,
                               String writer, List<AdminBoardFileDTO> adminBoardFiles) {
        this.boardId = boardId;
        this.boardTitle = boardTitle;
        this.boardContent = boardContent;
        this.boardViewCount = boardViewCount;
        this.boardRegDate = boardRegDate;
        this.boardUpdate = boardUpdate;
        this.boardType = boardType;
        this.boardYN = boardYN;
        this.writer = writer;
        this.adminBoardFiles = adminBoardFiles;
    }
}
