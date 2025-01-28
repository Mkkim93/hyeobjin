package com.hyeobjin.domain.repository.board;

import com.hyeobjin.application.admin.dto.board.DetailAdminBoardDTO;
import com.hyeobjin.application.common.dto.board.BoardDetailDTO;
import com.hyeobjin.application.common.dto.board.UpdateBoardDTO;

public interface BoardRepositoryCustom {

    BoardDetailDTO findByBoardDetail(Long boardId);

    // admin
    UpdateBoardDTO updateBoard(UpdateBoardDTO updateBoardDTO);

    DetailAdminBoardDTO findByBoardDetailAdmin(Long boardId);
}
