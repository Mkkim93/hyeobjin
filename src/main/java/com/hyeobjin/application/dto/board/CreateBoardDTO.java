package com.hyeobjin.application.dto.board;

import com.hyeobjin.domain.entity.board.Board;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

/**
 * 게시글 생성 DTO
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
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

    private List<FileBoxBoardDTO> boardFiles;

    public Board toEntity(CreateBoardDTO createBoardDTO) {

        return Board.builder()
                .boardId(createBoardDTO.getBoardId())
                .boardTitle(createBoardDTO.getBoardTitle())
                .boardContent(createBoardDTO.getBoardContent())
                .userId(createBoardDTO.getUsersId())
                .build();
    }
}
