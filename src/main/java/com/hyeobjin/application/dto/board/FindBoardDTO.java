package com.hyeobjin.application.dto.board;


import com.hyeobjin.domain.entity.board.Board;
import com.hyeobjin.domain.entity.file.FileBox;
import com.hyeobjin.domain.entity.users.Users;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.domain.Page;

import java.time.LocalDateTime;
import java.util.List;

@Data
@NoArgsConstructor
public class FindBoardDTO {

    private Long boardId;
    private String boardTitle;
    private Long boardViewCount;
    private LocalDateTime boardRegDate;
    private String username;

    public FindBoardDTO (Long boardId, String boardTitle, Long boardViewCount, LocalDateTime boardRegDate, String username) {
        this.boardId = boardId;
        this.boardTitle = boardTitle;
        this.boardViewCount = boardViewCount;
        this.boardRegDate = boardRegDate;
        this.username = username;
    }
}
