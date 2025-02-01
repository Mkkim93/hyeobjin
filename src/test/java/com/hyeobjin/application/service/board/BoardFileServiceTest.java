package com.hyeobjin.application.service.board;

import com.hyeobjin.application.common.dto.board.BoardFileDTO;
import com.hyeobjin.application.common.dto.board.CreateBoardDTO;
import com.hyeobjin.application.common.dto.board.FileBoxBoardDTO;
import com.hyeobjin.application.common.service.board.BoardFileService;
import com.hyeobjin.application.common.service.board.BoardService;
import com.hyeobjin.domain.entity.board.Board;
import com.hyeobjin.domain.entity.file.FileBox;
import com.hyeobjin.domain.repository.file.FileBoxRepository;
import lombok.extern.slf4j.Slf4j;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

@Slf4j
@ExtendWith(MockitoExtension.class)
@SpringBootTest
class BoardFileServiceTest {

    @Autowired
    private FileBoxRepository fileBoxRepository;

    @Autowired
    private BoardFileService boardFileService;

    @Autowired
    private BoardService boardService;

    @Test
    @DisplayName("게시판 파일 경로 파일 저장 테스트")
    void saveFilesOnly() throws IOException {
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

        boardFileService.fileSave(fileBoxBoardDTO, files);

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

//        boardService.save(createBoardDTO, files);
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
        Long boardId = 41L;
        Long fileBoxId = 41L;
        boardFileService.delete(fileBoxId, boardId);

        Board board = boardService.existById(boardId);
        Optional<FileBox> fileBox = fileBoxRepository.findById(fileBoxId);

        assertThat(board).isNotNull(); // 게시글은 여전히 존재
        assertThat(fileBox).isEmpty(); // 게시글의 파일은 삭제
    }
}