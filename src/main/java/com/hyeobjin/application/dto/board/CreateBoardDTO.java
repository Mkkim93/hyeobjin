package com.hyeobjin.application.dto.board;

import com.hyeobjin.domain.entity.file.FileBox;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Data
@NoArgsConstructor
public class CreateBoardDTO {

    private Long boardId;
    private String boardPass;
    private String boardTitle;
    private String boardContent;
    private Long boardViewCount;
    private String boardType;
    private LocalDateTime boardUpdate;
    private String boardYN;

    private Long usersId;

    private List<FileBox> files;
}
