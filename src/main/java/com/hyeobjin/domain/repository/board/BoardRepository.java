package com.hyeobjin.domain.repository.board;

import com.hyeobjin.domain.entity.board.Board;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface BoardRepository extends JpaRepository<Board, Long> {

    Board findByBoardTitle(String boardTitle);

    @Query("select b from Board b where b.boardYN = 'Y' order by b.boardRegDate desc ")
    Page<Board> findByAllBoardList(Pageable pageable);

    @Modifying
    @Query("update Board b set b.boardYN = 'Y' where b.id = :boardId")
    Integer deleteBoard(@Param("boardId") Long boardId);

    Page<Board> findByBoardYNAndBoardTitleContaining(String boardYN, String searchKeyWord, Pageable pageable);

    // admin
    @EntityGraph(attributePaths = {"users"})
    @Query("select b from Board b, Users u where b.users.id = u.id order by b.boardRegDate desc")
    Page<Board> findByAllBoardListAdmin(Pageable pageable);

    @EntityGraph(attributePaths = {"users"})
    Page<Board> findByBoardTitleContaining(Pageable pageable, String searchKeyword);
}
