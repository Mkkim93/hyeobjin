package com.hyeobjin.application.service.board;

import com.hyeobjin.application.dto.board.BoardFileDTO;
import com.hyeobjin.application.dto.board.CreateBoardDTO;
import com.hyeobjin.domain.entity.board.Board;
import com.hyeobjin.domain.repository.file.FileBoxRepository;
import lombok.extern.slf4j.Slf4j;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
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
@SpringBootTest
class BoardFileServiceTest {

    @Autowired
    private BoardFileService boardFileService;

    @Autowired
    private BoardService boardService;

    @Test
    @DisplayName("게시판 파일 경로 파일 저장 테스트")
    void saveFilesOnly() throws IOException {
        BoardFileDTO boardFileDTO = new BoardFileDTO();
        boardFileDTO.setBoardId(1L);

        List<MultipartFile> files = new ArrayList<>();

        boardFileDTO.setFileName("test board fileName 01");
        boardFileDTO.setFileType("jpeg/image");
        boardFileDTO.setFileSize(123L);
        boardFileDTO.setFileOrgName("test board fileOrgName");

        MockMultipartFile mockFile = new MockMultipartFile(
                "file",
                "testFile.jpg",
                "image/jpeg",
                "Test file content".getBytes());

        files.add(mockFile);

        boardFileService.fileSave(boardFileDTO, files);

        assertFalse(files.isEmpty());
    }

    @Test
    @DisplayName("게시글 & 다중 파일 저장")
    void saveBoardAndFiles() throws IOException {

        CreateBoardDTO createBoardDTO = new CreateBoardDTO();
        createBoardDTO.setBoardTitle("게시글 & 파일 저장 제목 07");
        createBoardDTO.setBoardContent("게시글 & 파일 저장 내용 07");
        createBoardDTO.setUsersId(2L);

        List<MultipartFile> files = new ArrayList<>();

        MockMultipartFile mockFile1 = new MockMultipartFile(
                "file01",
                "testFile01.jpg",
                "image/jpeg",
                "Test file content".getBytes());

        MockMultipartFile mockFile2 = new MockMultipartFile(
                "file02",
                "testFile02.jpg",
                "image/jpeg",
                "Test file content".getBytes());

        MockMultipartFile mockFile3 = new MockMultipartFile(
                "file03",
                "testFile03.jpg",
                "image/jpeg",
                "Test file content".getBytes());

        MockMultipartFile mockFile4 = new MockMultipartFile(
                "file04",
                "testFile04.jpg",
                "image/jpeg",
                "Test file content".getBytes());

        MockMultipartFile mockFile5 = new MockMultipartFile(
                "file05",
                "testFile05.jpg",
                "image/jpeg",
                "Test file content".getBytes());

        MockMultipartFile mockFile6 = new MockMultipartFile(
                "file06",
                "testFile06.jpg",
                "image/jpeg",
                "Test file content".getBytes());

        files.add(mockFile1);
        files.add(mockFile2);
        files.add(mockFile3);
        files.add(mockFile4);
        files.add(mockFile5);
        files.add(mockFile6);

        boardService.save(createBoardDTO, files);
    }

    @Test
    @DisplayName("기존 게시글에 새로운 파일 데이터 추가")
    void addFile() throws IOException {
        Long boardId = 15L;
        MockMultipartFile addMockFile = new MockMultipartFile(
                "file06",
                "testFile06.jpg",
                "image/jpeg",
                "Test file content".getBytes());

        List<MultipartFile> files = new ArrayList<>();

        files.add(addMockFile);

        boardFileService.saveFileOnly(boardId, files);
    }

    @Test
    @DisplayName("기존 게시글에 존재하는 파일 삭제")
    void deleteFileOnly() {
        Long boardId = 15L;
        Long fileBoxId = 5L;
        boardFileService.delete(fileBoxId, boardId);

        Board board = boardService.existById(boardId);

        assertThat(board).isNull();
    }
}