package com.hyeobjin.application.service.board;

import com.hyeobjin.application.dto.board.BoardDetailDTO;
import com.hyeobjin.application.dto.board.CreateBoardDTO;
import com.hyeobjin.application.dto.board.BoardListDTO;
import com.hyeobjin.application.dto.board.UpdateBoardDTO;
import com.hyeobjin.domain.entity.board.Board;
import com.hyeobjin.domain.repository.board.BoardRepository;
import com.hyeobjin.domain.repository.board.BoardRepositoryImpl;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

/*
 파일 데이터와 연관 관계를 갖지 않는 서비스
 1) 게시글 목록
 2) 게시글 타입별 단순 조회 (나중에 새로운 게시판이 추가될 것을 고려)
 */
@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class BoardService {

    private final BoardRepository boardRepository;
    private final BoardRepositoryImpl boardRepositoryImpl;
    private final BoardFileService boardFileService;

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
    public Page<BoardListDTO> findAll(Pageable pageable) {

        Page<Board> boardList = boardRepository.findByAllBoardList(pageable);

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
     * @param boardId 게시글 번호를 전송하여 해당 게시글의 모든 데이터(게시글 콘텐츠 & 파일데이터)를 조회한다.
     * @return
     */
    public BoardDetailDTO findDetail(Long boardId) {

        boardRepository.findById(boardId)
                .orElseThrow(() -> new EntityNotFoundException("해당 게시글번호가 존재하지 않습니다."));

        return boardRepositoryImpl.findByBoardDetail(boardId);

    }

    /**
     * 게시글 작성
     * # test code : O
     * @param createBoardDTO
     */
    public void save(CreateBoardDTO createBoardDTO, List<MultipartFile> files) throws IOException {

        Board saved = boardRepository.save(new Board().saveToEntity(createBoardDTO));

        if (files != null && !files.isEmpty()) {
            log.info("files is board save");
            boardFileService.saveFilesForBoard(saved, files);
        }
    }

    /**
     * 게시글 삭제
     * test code : O
     * @param boardId 사용자가 삭제할 게시글 번호을 전송하면 해당 게시글번호가 존재여부 검즈엥 성공하면 게시글을 삭제한다.
     */
    public void delete(Long boardId) {

        boardRepository.findById(boardId)
                .orElseThrow(() -> new EntityNotFoundException("게시물 조회에 실패하였습니다. 해당 게시글이 존재하지 않습니다."));

        boardRepository.deleteBoard(boardId);
    }

    /**
     * 수정할 게시글의 데이터를 DTO 객체에 담아 로직을 수행한다.
     * 게시글 제목만 수정 시 이전의 게시글 내용은 유지하고 제목만 수정한다.
     * 반대로 게시글 내용만 수정 시 이전의 게시글 제목은 유지하고 내용만 수정한다.
     * test code : O
     * @param updateBoardDTO 업데이트 객체
     */
    public void update(UpdateBoardDTO updateBoardDTO) {
        boardRepositoryImpl.updateBoard(updateBoardDTO);
    }
}