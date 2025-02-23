package com.hyeobjin.application.service.board;

import com.hyeobjin.application.common.dto.board.FileBoxBoardDTO;
import com.hyeobjin.application.common.service.board.BoardFileService;
import com.hyeobjin.application.common.service.board.BoardService;
import com.hyeobjin.domain.entity.board.Board;
import com.hyeobjin.domain.repository.file.FileBoxRepository;
import jakarta.persistence.EntityManager;
import lombok.extern.slf4j.Slf4j;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

@Slf4j
@ExtendWith(MockitoExtension.class)
@Transactional
@DisplayName("게시판/파일 테스트")
@SpringBootTest
class BoardFileServiceTest {

    @Autowired
    private FileBoxRepository fileBoxRepository;

    @Autowired
    private BoardFileService boardFileService;

    @Autowired
    private BoardService boardService;

    @Autowired
    private EntityManager entityManager;

    @Test
    @DisplayName("등록 : 게시판 파일 경로 파일 저장 테스트")
    void saveFilesOnly() throws IOException {

        // given
        FileBoxBoardDTO fileBoxBoardDTO = new FileBoxBoardDTO();
        fileBoxBoardDTO.setBoardId(1L);

        List<MultipartFile> files = new ArrayList<>();

        fileBoxBoardDTO.setFileName("test board fileName 01");
        fileBoxBoardDTO.setFileType("jpeg/image");
        fileBoxBoardDTO.setFileSize(123L);
        fileBoxBoardDTO.setFileOrgName("test board fileOrgName");

        MockMultipartFile mockFile = new MockMultipartFile(
                "file",
                "testFile.jpg",
                "image/jpeg",
                "Test file content".getBytes());

        files.add(mockFile);

        // when
        boardFileService.fileSave(fileBoxBoardDTO, files);

        entityManager.flush();
        entityManager.clear();

        // then
        assertFalse(files.isEmpty());
    }

    @Test
    @DisplayName("등록 : 기존 게시글에 새로운 파일 데이터 추가")
    void addFile() throws IOException {

        // given
        Long boardId = 1L;
        MockMultipartFile addMockFile = new MockMultipartFile(
                "file06",
                "testFile06.jpg",
                "image/jpeg",
                "Test file content".getBytes());

        List<MultipartFile> files = new ArrayList<>();

        files.add(addMockFile);

        // when
        boardFileService.saveFileOnly(boardId, files);

        // then
        Boolean exists = fileBoxRepository.existsByBoardId(1L);

        assertThat(exists).isTrue();
    }

    @Test
    @DisplayName("삭제 : 기존 게시글에 존재하는 파일 삭제")
    void deleteFileOnly() {

        // given
        Long boardId = 1L;
        Long fileBoxId = 13L;

        // when
        boardFileService.delete(fileBoxId, boardId);

        Board board = boardService.existById(boardId);
        boolean exist = fileBoxRepository.existsById(fileBoxId);

        // then
        assertThat(board).isNotNull(); // 게시글은 여전히 존재
        assertThat(exist).isFalse(); // 게시글의 파일은 삭제
    }
}