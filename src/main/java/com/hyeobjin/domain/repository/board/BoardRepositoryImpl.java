package com.hyeobjin.domain.repository.board;


import com.hyeobjin.application.admin.dto.board.DetailAdminBoardDTO;
import com.hyeobjin.application.admin.dto.file.AdminBoardFileDTO;
import com.hyeobjin.application.admin.dto.file.QAdminBoardFileDTO;
import com.hyeobjin.application.common.dto.board.BoardDetailDTO;
import com.hyeobjin.application.common.dto.board.BoardFileDTO;
import com.hyeobjin.application.common.dto.board.QBoardFileDTO;
import com.hyeobjin.application.common.dto.board.UpdateBoardDTO;
import com.hyeobjin.domain.entity.board.Board;
import com.hyeobjin.domain.entity.board.QBoard;
import com.hyeobjin.domain.entity.file.QFileBox;
import com.hyeobjin.domain.entity.users.QUsers;

import com.querydsl.jpa.impl.JPAQueryFactory;
import com.querydsl.jpa.impl.JPAUpdateClause;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

import static com.hyeobjin.domain.entity.board.QBoard.*;

@Slf4j
@Repository
@Transactional
public class BoardRepositoryImpl extends QuerydslRepositorySupport implements BoardRepositoryCustom {

    private final JPAQueryFactory jpaQueryFactory;

    public BoardRepositoryImpl(EntityManager em) {
        super(Board.class);
        jpaQueryFactory = new JPAQueryFactory(em);
    }

    @Override
    public BoardDetailDTO findByBoardDetail(Long boardId) {

        QBoard board = QBoard.board;
        QFileBox fileBox = QFileBox.fileBox;
        QUsers users = QUsers.users;

        Board boardEntity = jpaQueryFactory.selectFrom(board)
                .leftJoin(board.users, users).fetchJoin()
                .where(board.id.eq(boardId))
                .fetchOne();

        if (boardEntity == null) {
            log.info("게시글 조회 실패 ={}", (Object) null);
            // TODO 예외처리
            return null;
        }

        // 현재 boardId 에 해당하는 files 를 컬렉션으로 반환
        List<BoardFileDTO> boardFiles = jpaQueryFactory
                .select(new QBoardFileDTO(
                        fileBox.id,
                        fileBox.fileName,
                        fileBox.fileOrgName,
                        fileBox.fileSize,
                        fileBox.fileType,
                        fileBox.filePath,
                        fileBox.fileRegDate,
                        fileBox.board.id))
                .from(fileBox)
                .where(fileBox.board.id.eq(boardId))
                .fetch();

        return new BoardDetailDTO(
                boardEntity.getId(),
                boardEntity.getBoardTitle(),
                boardEntity.getBoardContent(),
                boardEntity.getBoardViewCount(),
                boardEntity.getBoardRegDate(),
                boardEntity.getBoardUpdate(),
                boardEntity.getUsers().getName(),
                boardFiles
        );
    }

    @Override
    public Board updateBoard(UpdateBoardDTO updateBoardDTO) { // dto null

        EntityManager entityManager = getEntityManager();

        JPAUpdateClause updateClause = new JPAUpdateClause(entityManager, board);

        if (updateBoardDTO.getBoardTitle() != null) {
            updateClause.set(board.boardTitle, updateBoardDTO.getBoardTitle());
        }
        if (updateBoardDTO.getBoardContent() != null) {
            updateClause.set(board.boardContent, updateBoardDTO.getBoardContent());
        }
        if (updateBoardDTO.getBoardYN() != null) {
            updateClause.set(board.boardYN, updateBoardDTO.getBoardYN());
        }
        updateClause.set(board.boardUpdate, LocalDateTime.now()); // 게시글 수정 시 현재 시간으로 업데이트

        updateClause.where(board.id.eq(updateBoardDTO.getBoardId()));

        long updateCount = updateClause.execute();

        if (updateCount > 0) {
            return new Board(
                    updateBoardDTO.getBoardId()

            );
        } else {
            throw new EntityNotFoundException("해당 게시글이 존재하지 않습니다.");
        }
    }

    @Override
    public DetailAdminBoardDTO findByBoardDetailAdmin(Long boardId) {

        QBoard board = QBoard.board;
        QFileBox fileBox = QFileBox.fileBox;
        QUsers users = QUsers.users;

        Board boardEntity = jpaQueryFactory
                .selectFrom(board)
                .leftJoin(board.users, users).fetchJoin()
                .where(board.id.eq(boardId))
                .fetchOne();

        if (boardEntity == null) {
            log.info("관리자 페이지 게시글 조회 실패={}", (Object) null);
            // TODO 예외 처리
            return null;
        }

        List<AdminBoardFileDTO> adminBoardFiles = jpaQueryFactory.select(new QAdminBoardFileDTO(
                        fileBox.id,
                        fileBox.fileName,
                        fileBox.fileOrgName,
                        fileBox.fileSize,
                        fileBox.fileType,
                        fileBox.filePath,
                        fileBox.fileRegDate,
                        fileBox.board.id))
                .from(fileBox)
                .where(fileBox.board.id.eq(boardId))
                .fetch();

        return new DetailAdminBoardDTO(
                boardEntity.getId(),
                boardEntity.getBoardTitle(),
                boardEntity.getBoardContent(),
                boardEntity.getBoardViewCount(),
                boardEntity.getBoardRegDate(),
                boardEntity.getBoardUpdate(),
                boardEntity.getBoardType(),
                boardEntity.getBoardYN(),
                boardEntity.getUsers().getName(),
                adminBoardFiles
        );

    }
}
