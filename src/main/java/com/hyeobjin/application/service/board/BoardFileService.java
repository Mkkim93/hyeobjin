package com.hyeobjin.application.service.board;

import com.hyeobjin.application.dto.board.BoardFileDTO;
import com.hyeobjin.application.dto.board.FileBoxBoardDTO;
import com.hyeobjin.domain.entity.board.Board;
import com.hyeobjin.domain.entity.file.FileBox;
import com.hyeobjin.domain.repository.file.FileBoxRepository;
import jakarta.annotation.PostConstruct;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * 게시글을 저장할 때 파일 데이터 관리는 현재 별도의 서비스에서 처리
 */
@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class BoardFileService {

    @Value("${file.board.dir}")
    private String fileDir;
    private final FileBoxRepository fileBoxRepository;

    // 파일 다운로드를 위한 전체 경로
    public String getFullPath(String fileName) {
        return fileDir + fileName;
    }

    /**
     * 모든 파일이 현재 서비스 레이어에서 저장되기 이전에 해당 파일의 경로를 검증
     */
    @PostConstruct
    public void ensureDirectoryExists() {
        File directory = new File(fileDir);
        log.info("board fileDir ={}", fileDir);
        if (!directory.exists()) {
            try {
                directory.mkdirs();
            } catch (SecurityException e) {
                throw new IllegalStateException("게시판 파일 저장 디렉토리를 찾을 수 없습니다.");
            }
        }
    }

    // 최종 파일 저장 fileSave() 이전에 해당 파일의 예외 가능성을 확인하 는 메서드
    public void saveFilesForBoard(Board board, List<MultipartFile> files) throws IOException {

        FileBoxBoardDTO fileBoxBoardDTO = new FileBoxBoardDTO();
        fileBoxBoardDTO.setBoardId(board.getId());

        try {

            fileSave(fileBoxBoardDTO, files);

        } catch (IOException e) {
            log.info("파일 저장 중 오류 발생 : {}", e.getMessage(), e);
            throw e;
        }
    }

    /**
     * 파일을 정적 파일 경로에 저장하고, 메타데이터를 DTO 객체를 통해 가지고와서 엔티티로 변환 후 DB에 저장한다.
     * test code : O
     * @param fileBoxBoardDTO 파일 메타데이터 DTO
     * @param files 정적파일에 저장할 file 객체
     * @throws IOException
     */
    public void fileSave(FileBoxBoardDTO fileBoxBoardDTO, List<MultipartFile> files) throws IOException {

        List<FileBox> fileBoxes = new ArrayList<>();

        for (MultipartFile file : files) {
            if (file.isEmpty()) {
                continue;
            }

            String filePath = fileDir;
            UUID uuid = UUID.randomUUID();
            String fileName = uuid + "_" + file.getOriginalFilename();
            File saveFile = new File(filePath, fileName);

            file.transferTo(saveFile);

            FileBox savedFiles = FileBox.builder()
                    .fileOrgName(file.getOriginalFilename())
                    .fileName(fileName)
                    .filePath(filePath + fileName)
                    .fileSize(file.getSize())
                    .fileType(file.getContentType())
                    .boardId(Board.builder()
                            .boardId(fileBoxBoardDTO.getBoardId())
                            .build())
                    .build();

            fileBoxes.add(savedFiles);

            fileBoxRepository.save(savedFiles);
        }
    }

    /**
     * 현재 게시글번호를 조회하고 게시글이 존재하면 새로운 파일을 추가
     * test code : O
     * @param boardId 게시글 번호
     * @param files 파일 데이터
     * @throws IOException
     */
    public void saveFileOnly(Long boardId, List<MultipartFile> files) throws IOException {
        fileSave(new FileBoxBoardDTO(boardId), files);
    }

    /**
     * 파일 삭제 (정적경로의 파일 우선 삭제 후 DB 메타데이터 삭제)
     * RuntimeException : 정적 파일 삭제 실패 시, 런타임 예외 발생 메타데이터 삭제 로직이 실행 되지 않도록 한다.
     * test code : O
     * @param fileBoxId 삭제할 파일 번호
     * @param boardId 삭제할 파일을 가진 게시글 번호
     */
    public void delete(Long fileBoxId, Long boardId) {

        Boolean exists = fileBoxRepository.existsByBoardId(boardId);

        if (!exists) {
            throw new RuntimeException("삭제하려는 파일을 참조하는 게시글이 존재하지 않습니다.");
        }

        FileBox fileBox = fileBoxRepository.findByBoardIdAndId(boardId, fileBoxId);

        File file = new File(fileBox.getFilePath());

        if (file.exists()) {
            boolean fileDeleted = file.delete();
            if (!fileDeleted) {
                throw new RuntimeException("정적 파일 삭제 오류");
            }
        }
        fileBoxRepository.delete(fileBox);
        log.info("게시글의 파일이 성공적으로 삭제 되었습니다.");
    }

    public FileBox findById(Long fileBoxId) {
        return fileBoxRepository.findById(fileBoxId).orElseThrow(() -> new EntityNotFoundException("해당 파일이 존재하지 않습니다."));
    }
}
