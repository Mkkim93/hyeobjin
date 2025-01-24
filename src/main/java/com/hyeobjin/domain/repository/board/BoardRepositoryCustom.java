package com.hyeobjin.domain.repository.board;

import com.hyeobjin.application.common.dto.board.BoardDetailDTO;
import com.hyeobjin.application.common.dto.board.UpdateBoardDTO;

public interface BoardRepositoryCustom {

    BoardDetailDTO findByBoardDetail(Long boardId);

    UpdateBoardDTO updateBoard(UpdateBoardDTO updateBoardDTO);
}
