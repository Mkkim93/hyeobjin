package com.hyeobjin.application.admin.dto.board;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class FindAdminBoardDTO {

    private Long boardId;
    private String boardTitle;
    private Long boardViewCount;
    private LocalDateTime boardRegDate;
    private LocalDateTime boardUpdate;
    private String boardType;
    private String boardYN;
    private String writer; // Users = name
}
