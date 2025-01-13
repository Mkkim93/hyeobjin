package com.hyeobjin.domain.repository.board;

import com.hyeobjin.application.dto.board.BoardDetailDTO;
import com.hyeobjin.application.dto.board.UpdateBoardDTO;

public interface BoardRepositoryCustom {

    BoardDetailDTO findByBoardDetail(Long boardId);

    UpdateBoardDTO updateBoard(UpdateBoardDTO updateBoardDTO);
}
