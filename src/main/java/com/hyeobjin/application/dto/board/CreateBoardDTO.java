package com.hyeobjin.application.dto.board;

import com.hyeobjin.application.dto.file.FileBoxItemDTO;
import com.hyeobjin.domain.entity.board.Board;
import com.hyeobjin.domain.entity.file.FileBox;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;
import java.util.List;

/**
 * 게시글 생성 DTO
 */
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

    private List<FileBoxBoardDTO> files;

    public CreateBoardDTO(Long boardId) {
        this.boardId = boardId;
    }
}
