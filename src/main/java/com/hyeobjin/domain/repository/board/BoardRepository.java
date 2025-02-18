package com.hyeobjin.domain.repository.board;

import com.hyeobjin.domain.entity.board.Board;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface BoardRepository extends JpaRepository<Board, Long> {

    Board findByBoardTitle(String boardTitle);

    @Query("select b from Board b where b.boardYN = 'Y' and b.boardType = :boardType order by b.boardRegDate desc ")
    Page<Board> findByAllBoardList(Pageable pageable, @Param("boardType") String boardType);

    @Modifying
    @Query("update Board b set b.boardYN = 'Y' where b.id = :boardId")
    Integer deleteBoard(@Param("boardId") Long boardId);

    Page<Board> findByBoardYNAndBoardTitleContainingAndBoardType(
            String boardYN, String searchKeyWord, String boardType, Pageable pageable);


    // admin
    @EntityGraph(attributePaths = {"users"})
    @Query("select b from Board b, Users u where b.users.id = u.id order by b.boardRegDate desc")
    Page<Board> findByAllBoardListAdmin(Pageable pageable);

    @EntityGraph(attributePaths = {"users"})
    Page<Board> findByBoardTitleContaining(Pageable pageable, String searchKeyword);

    List<Board> findTop2ByOrderByBoardUpdateDesc();

    @Modifying
    @Query("update Board b set b.boardViewCount = b.boardViewCount + 1 where b.id = :boardId")
    int updateBoardViewCount(@Param("boardId") Long boardId);
}
