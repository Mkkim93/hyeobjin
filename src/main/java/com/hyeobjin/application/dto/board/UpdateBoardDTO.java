package com.hyeobjin.application.dto.board;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UpdateBoardDTO {

    @NotNull
    private Long boardId;

    private String boardTitle;
    private String content;
    private List<FileBoxBoardDTO> files;
}
