package com.hyeobjin.application.common.service.board;

import com.hyeobjin.application.common.dto.board.BoardDetailDTO;
import com.hyeobjin.application.common.dto.board.BoardListDTO;
import com.hyeobjin.domain.entity.board.Board;
import com.hyeobjin.domain.repository.board.BoardRepository;
import com.hyeobjin.domain.repository.board.BoardRepositoryImpl;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/*
 파일 데이터와 연관 관계를 갖지 않는 서비스
 1) 게시글 목록
 2) 게시글 타입별 단순 조회 (나중에 새로운 게시판이 추가될 것을 고려)
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class BoardService {

    private final BoardRepository boardRepository;
    private final BoardRepositoryImpl boardRepositoryImpl;

    public Board existById(Long boardId) {
        boolean exists = boardRepository.existsById(boardId);

        if (!exists) {
             throw new EntityNotFoundException("해당 게시글이 존재하지 않습니다.");
        }
        return boardRepository.findById(boardId)
                .orElseThrow(() -> new EntityNotFoundException("해당 게시글이 존재 하지 않습니다."));
    }

    /**
     * 게시글 전체 목록을 조회
     * # test code : O
     * @param pageable
     * @return
     */
    public Page<BoardListDTO> findAll(Pageable pageable, String boardType) {

        Page<Board> boardList = boardRepository.findByAllBoardList(pageable, boardType);

        return boardList.map(board -> {
            return new BoardListDTO(
                    board.getId(),
                    board.getBoardTitle(),
                    board.getBoardViewCount(),
                    board.getBoardRegDate(),
                    board.getUsers().getName());
        });
    }

    /**
     * 게시글 상세 조회
     * test code : O
     * ROLE : COMMON
     * @param boardId 게시글 번호를 전송하여 해당 게시글의 모든 데이터(게시글 콘텐츠 & 파일데이터)를 조회한다.
     * @return
     */
    public BoardDetailDTO findDetail(Long boardId) {
        return boardRepositoryImpl.findByBoardDetail(boardId);
    }

    @Transactional
    public void updateViewCount(Long boardId) {
        boardRepository.updateBoardViewCount(boardId);
    }
}