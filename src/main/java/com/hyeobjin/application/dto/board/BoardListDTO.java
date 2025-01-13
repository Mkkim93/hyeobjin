package com.hyeobjin.application.dto.board;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

/**
 * 게시글 목록 조회 DTO
 */
@Data
@NoArgsConstructor
public class BoardListDTO {

    private Long boardId;
    private String boardTitle;
    private Long boardViewCount;
    private LocalDateTime boardRegDate;
    private String writer; // Users = name

    public BoardListDTO(Long boardId, String boardTitle, Long boardViewCount,
                        LocalDateTime boardRegDate, String writer) {
        this.boardId = boardId;
        this.boardTitle = boardTitle;
        this.boardViewCount = boardViewCount;
        this.boardRegDate = boardRegDate;
        this.writer = writer;
    }
}
