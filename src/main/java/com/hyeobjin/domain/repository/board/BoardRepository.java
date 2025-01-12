package com.hyeobjin.domain.repository.board;

import com.hyeobjin.domain.entity.board.Board;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BoardRepository extends JpaRepository<Board, Long> {

    Board findByBoardTitle(String boardTitle);
}
