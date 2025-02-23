package com.hyeobjin.application.service.file;

import com.hyeobjin.application.admin.dto.file.UpdateItemFileDTO;
import com.hyeobjin.application.admin.service.file.AdminItemFileService;
import com.hyeobjin.domain.entity.file.FileBox;
import com.hyeobjin.domain.entity.item.Item;
import com.hyeobjin.domain.repository.file.FileBoxRepository;
import jakarta.persistence.EntityManager;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@Slf4j
@Transactional
@ExtendWith(MockitoExtension.class)
@DisplayName("파일/제품 테스트")
@SpringBootTest
class FileBoxItemServiceTest {

    @Mock
    private FileBoxRepository fileBoxRepository;

    @InjectMocks
    private AdminItemFileService fileBoxItemService;

    @Autowired
    private EntityManager entityManager;

    @Test
    @DisplayName("등록 : 파일/제품 저장")
    void save() throws IOException {

        // given
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

        // when
        fileBoxItemService.fileSave(createFileBoxDTO, files);

        // then
        assertFalse(files.isEmpty());
    }

    @Test
    @DisplayName("등록 : 파일만 저장")
    void fileOnlySave() throws IOException {

        // given
        Long itemId = 1L;
        UpdateItemFileDTO createFileBoxDTO = new UpdateItemFileDTO();
        createFileBoxDTO.setFileName("test fileName03");
        createFileBoxDTO.setFileType("jpeg/image");
        createFileBoxDTO.setFileSize(1234L);
        createFileBoxDTO.setFileOrgName("test fileOrgName");
        createFileBoxDTO.setItemId(itemId);

        List<MultipartFile> files = new ArrayList<>();

        MockMultipartFile mockFile = new MockMultipartFile(
                "file",
                "testFile.jpg",
                "image/jpeg",
                "Test file content".getBytes());

        files.add(mockFile);

        // when
        verify(fileBoxRepository,
                times(1)).save(any(FileBox.class));

        ArgumentCaptor<FileBox> captor = ArgumentCaptor.forClass(FileBox.class);
        verify(fileBoxRepository).save(captor.capture());

        FileBox savedFile = captor.getValue();

        // then
        assertNull(savedFile);
    }

    @Test
    @DisplayName("삭제 : 정적 파일 영구 삭제")
    void fileDelete() {

        // given
        Long fileBoxId = 15L;

        // when
        fileBoxItemService.deleteFile(fileBoxId);

//        entityManager.flush();
//        entityManager.clear();

        boolean exist = fileBoxRepository.existsById(1L);

        assertThat(exist).isFalse();

    }
}