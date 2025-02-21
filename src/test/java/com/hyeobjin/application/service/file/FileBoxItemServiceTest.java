package com.hyeobjin.application.service.file;

import com.hyeobjin.application.admin.dto.file.UpdateItemFileDTO;
import com.hyeobjin.application.admin.service.file.AdminItemFileService;
import com.hyeobjin.domain.entity.file.FileBox;
import com.hyeobjin.domain.entity.item.Item;
import com.hyeobjin.domain.repository.file.FileBoxRepository;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
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
class FileBoxItemServiceTest {

    @Mock
    private FileBoxRepository fileBoxRepository;

    @InjectMocks
    private AdminItemFileService fileBoxItemService;

    @Test
    @DisplayName("file & Item save test")
    void save() throws IOException {
        UpdateItemFileDTO createFileBoxDTO = new UpdateItemFileDTO();

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

        fileBoxItemService.fileSave(createFileBoxDTO, files);

        assertFalse(files.isEmpty());
    }

    @Test
    @DisplayName("file only save test")
    void fileOnlySave() throws IOException {
        Long itemId = 1L;
        UpdateItemFileDTO createFileBoxDTO = new UpdateItemFileDTO();
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
        fileBoxItemService.deleteFile(fileBoxId);
    }
}