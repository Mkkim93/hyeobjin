package com.hyeobjin.application.dto.board;

import com.querydsl.core.annotations.QueryProjection;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.time.LocalDateTime;
import java.util.List;

/**
 * 게시글 상세 조회 DTO
 */
@Data
@NoArgsConstructor
public class BoardDetailDTO {

    private Long boardId;
    private String boardTitle;
    private String boardContent;
    private Long boardViewCount;
    private LocalDateTime boardRegDate;
    private LocalDateTime boardUpdate;
    private String writer; // 게시글 작성자 (Users = name)

    private List<BoardFileDTO> boardFiles;

    @QueryProjection
    public BoardDetailDTO(Long boardId, String boardTitle, String boardContent,
                          Long boardViewCount, LocalDateTime boardRegDate, LocalDateTime boardUpdate,
                          String writer, List<BoardFileDTO> boardFiles) {
        this.boardId = boardId;
        this.boardTitle = boardTitle;
        this.boardContent = boardContent;
        this.boardViewCount = boardViewCount;
        this.boardRegDate = boardRegDate;
        this.boardUpdate = boardUpdate;
        this.writer = writer;
        this.boardFiles = boardFiles;
    }

    @Override
    public String toString() {
        return "BoardDetailDTO{" +
                "boardId=" + boardId +
                ", boardTitle='" + boardTitle + '\'' +
                ", boardContent='" + boardContent + '\'' +
                ", boardViewCount=" + boardViewCount +
                ", boardRegDate=" + boardRegDate +
                ", boardUpdate=" + boardUpdate +
                ", writer='" + writer + '\'' +
                ", boardFiles=" + boardFiles +
                '}';
    }
}
