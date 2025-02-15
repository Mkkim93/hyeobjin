package com.hyeobjin.application.admin.service.board;

import com.hyeobjin.application.admin.dto.board.DetailAdminBoardDTO;
import com.hyeobjin.application.admin.dto.board.FindAdminBoardDTO;
import com.hyeobjin.application.admin.service.file.AdminBoardFileService;
import com.hyeobjin.application.common.dto.board.CreateBoardDTO;
import com.hyeobjin.application.common.dto.board.UpdateBoardDTO;
import com.hyeobjin.application.common.service.board.BoardFileService;
import com.hyeobjin.domain.entity.board.Board;
import com.hyeobjin.domain.repository.board.BoardRepository;
import com.hyeobjin.domain.repository.board.BoardRepositoryImpl;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.orm.ObjectOptimisticLockingFailureException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;


@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class AdminBoardService {

    private final BoardRepository boardRepository;
    private final BoardRepositoryImpl boardRepositoryImpl;
    private final BoardFileService boardFileService;
    private final AdminBoardFileService adminBoardFileService;

    /**
     * 게시글 번호를 리스트 형태로 가져와서 해당 게시글과 참조하는 모든 파일을 영구적으로 삭제한다.
     * @param boardIds 삭제할 게시글 번호들
     * @return
     */
    public void deleteBoardAndFiles(List<Long> boardIds) {

        try {
            CompletableFuture<Boolean> fileDeletion = CompletableFuture.supplyAsync(() -> {
                return adminBoardFileService.deleteByStaticFiles(boardIds);
            });

            // 동기화
            Boolean haseFiles = fileDeletion.join();

            if (!haseFiles) {
                boardRepository.deleteAllByIdInBatch(boardIds);
                log.info("파일이 없는 게시판 삭제 로직 종료");
                return;
            }

            boardRepository.deleteAllByIdInBatch(boardIds);
            log.info("파일이 있는 게시판 삭제 (파일 삭제될때까지 기다렸음)");

        } catch (ObjectOptimisticLockingFailureException e) {
            log.info("ObjectOptimisticLockingFailureException ={}", e);
        }
    }

    /**
     * 관리자 페이지에서의 게시글 조회
     * @param pageable page : 0, size : 10
     * @return
     */
    public Page<FindAdminBoardDTO> findByAllList(Pageable pageable) {

        Page<Board> boardList = boardRepository.findByAllBoardListAdmin(pageable);

        return boardList.map(board -> {
            return new FindAdminBoardDTO(
                    board.getId(),
                    board.getBoardTitle(),
                    board.getBoardViewCount(),
                    board.getBoardRegDate(),
                    board.getBoardUpdate(),
                    board.getBoardType(),
                    board.getBoardYN(),
                    board.getUsers().getName()
            );
        });
    }

    /**
     * 수정할 게시글의 데이터를 DTO 객체에 담아 로직을 수행한다.
     * 게시글 제목만 수정 시 이전의 게시글 내용은 유지하고 제목만 수정한다.
     * 반대로 게시글 내용만 수정 시 이전의 게시글 제목은 유지하고 내용만 수정한다.
     * test code : O
     * @param updateBoardDTO 업데이트 객체
     */
    public Long update(UpdateBoardDTO updateBoardDTO, List<MultipartFile> files) throws IOException {

        Board board = boardRepositoryImpl.updateBoard(updateBoardDTO);

        if (files != null && !files.isEmpty()) {
            boardFileService.saveFilesForBoard(board, files);
        }

        return board.getId();
    }

    /**
     * 게시글 작성
     * # test code : O
     * @param createBoardDTO 새로운 게시글 작성 데이터 DTO
     */
    public Long saveBoard(CreateBoardDTO createBoardDTO, List<MultipartFile> files, Long userId) throws IOException {

        Board board = createBoardDTO.toEntity(createBoardDTO, userId);
        Board savedBoard = boardRepository.save(board);

        if (files != null && !files.isEmpty()) {
            boardFileService.saveFilesForBoard(board, files);
        }
        return savedBoard.getId();
    }



    /**
     * 관리자가 게시글 번호 (pk) 를 기준으로 게시글을 상세조회하기 위한 메서드
     * @param boardId 게시글 번호
     * @return DetailAdminBoardDTO 를 반환하고 파일 데이터는 내부 리스트 형대로 반환한다.
     */
    public DetailAdminBoardDTO findDetailBoard(Long boardId) {

        Board findBoardId = boardRepository.findById(boardId)
                .orElseThrow(() -> new EntityNotFoundException("해당 게시글이 존재하지 않습니다."));

        return boardRepositoryImpl.findByBoardDetailAdmin(findBoardId.getId());
    }

    public List<FindAdminBoardDTO> findBySimpleList() {
        List<Board> orderByBoardList = boardRepository.findTop2ByOrderByBoardUpdateDesc();

        return orderByBoardList.stream().map(board -> new FindAdminBoardDTO(
                board.getId(),
                board.getBoardTitle(),
                board.getUsers().getName(),
                board.getBoardUpdate()
        )).collect(Collectors.toList());
    }
}
