package com.hyeobjin.application.common.dto.board;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UpdateBoardDTO {

    private Long boardId;
    private String boardTitle;
    private String boardContent;

    private List<FileBoxBoardDTO> boardFiles;
}
