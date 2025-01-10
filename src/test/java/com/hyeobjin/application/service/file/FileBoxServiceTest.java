package com.hyeobjin.application.service.file;

import com.hyeobjin.application.dto.file.CreateFileBoxDTO;
import com.hyeobjin.domain.entity.Item;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@Slf4j
@SpringBootTest
@Transactional
class FileBoxServiceTest {

    @Autowired FileBoxService fileBoxService;

    @BeforeEach
    void setup() {
        fileBoxService.ensureDirectoryExists();
    }

    @Test
    @DisplayName("file save test")
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
}