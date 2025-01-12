package com.hyeobjin.application.service.file;

import com.hyeobjin.application.dto.file.CreateFileBoxDTO;
import com.hyeobjin.domain.entity.file.FileBox;
import com.hyeobjin.domain.entity.item.Item;
import com.hyeobjin.domain.repository.file.FileBoxRepository;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@Slf4j
@Transactional
@ExtendWith(MockitoExtension.class)
@SpringBootTest
class FileBoxServiceTest {

    @Mock
    private FileBoxRepository fileBoxRepository;

    @InjectMocks
    private FileBoxService fileBoxService;

//    @BeforeEach
//    void setup() {
//        fileBoxService.ensureDirectoryExists();
//    }

    @Test
    @DisplayName("file & Item save test")
    void save() throws IOException {
        CreateFileBoxDTO createFileBoxDTO = new CreateFileBoxDTO();

        List<MultipartFile> files = new ArrayList<>();
        createFileBoxDTO.setItemId(Item.builder()
                .itemId(createFileBoxDTO.getItemId())
                .build().getId());

        createFileBoxDTO.setFileName("test fileName03");
        createFileBoxDTO.setFileType("jpeg/image");
        createFileBoxDTO.setFileSize(1234L);
        createFileBoxDTO.setFileOrgName("test fileOrgName");

        // 임시 파일 생성
        MockMultipartFile mockFile = new MockMultipartFile(
                "file",
                "testFile.jpg",
                "image/jpeg",
                "Test file content".getBytes());

        files.add(mockFile);

        fileBoxService.fileSave(createFileBoxDTO, files);

        assertFalse(files.isEmpty());
    }

    @Test
    @DisplayName("file only save test")
    void fileOnlySave() throws IOException {
        Long itemId = 1L;
        CreateFileBoxDTO createFileBoxDTO = new CreateFileBoxDTO();
        createFileBoxDTO.setFileName("test fileName03");
        createFileBoxDTO.setFileType("jpeg/image");
        createFileBoxDTO.setFileSize(1234L);
        createFileBoxDTO.setFileOrgName("test fileOrgName");

        List<MultipartFile> files = new ArrayList<>();

        MockMultipartFile mockFile = new MockMultipartFile(
                "file",
                "testFile.jpg",
                "image/jpeg",
                "Test file content".getBytes());

        files.add(mockFile);

        fileBoxService.saveFileOnly(itemId, files);


        verify(fileBoxRepository,
                times(1)).save(any(FileBox.class));

        ArgumentCaptor<FileBox> captor = ArgumentCaptor.forClass(FileBox.class);
        verify(fileBoxRepository).save(captor.capture());

        FileBox savedFile = captor.getValue();
        assertNotNull(savedFile);
    }

    @Test
    @DisplayName("file delete metadata & static path all")
    void fileDelete() {
        Long fileBoxId = 1L;
        fileBoxService.deleteFile(fileBoxId);
    }
}