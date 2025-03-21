package com.hyeobjin.application.admin.dto.board;

import com.fasterxml.jackson.annotation.JsonView;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class FindAdminBoardDTO {

    public interface SummaryView {}

    private Long boardId;
    private String boardTitle;
    private Long boardViewCount;
    private LocalDateTime boardRegDate;
    private LocalDateTime boardUpdate;
    private String boardType;
    private String boardYN;
    private String writer; // Users = name

    public FindAdminBoardDTO(Long boardId, String boardTitle, String writer, LocalDateTime boardUpdate) {
        this.boardId = boardId;
        this.boardTitle = boardTitle;
        this.writer = writer;
        this.boardUpdate = boardUpdate;
    }
}
