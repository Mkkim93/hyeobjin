package com.hyeobjin.domain.repository;

import com.hyeobjin.application.dto.file.CreateFileBoxDTO;
import com.hyeobjin.domain.entity.file.FileBox;
import com.hyeobjin.domain.entity.item.Item;
import com.hyeobjin.domain.repository.file.FileBoxRepository;
import lombok.extern.slf4j.Slf4j;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;


import java.util.List;

import static org.assertj.core.api.Assertions.*;


@Slf4j
@SpringBootTest
class FileBoxRepositoryTest {

    @Autowired
    FileBoxRepository fileBoxRepository;

    @Test
    @DisplayName("fileBox connection test")
    void test() {
        List<FileBox> result = fileBoxRepository.findAll();
        result.stream().forEach(System.out::println);
        assertThat(result).isNull();
    }

    @Test
    @DisplayName("file save test")
    void save() {
        CreateFileBoxDTO createFileBoxDTO = new CreateFileBoxDTO();
        createFileBoxDTO.setItemId(Item.builder()
                        .itemId(createFileBoxDTO.getItemId())
                .build().getId());
        createFileBoxDTO.setFileName("test fileName 02");
        createFileBoxDTO.setFilePath("test filePath 02");
        createFileBoxDTO.setFileType("image/jpeg");
        createFileBoxDTO.setFileOrgName("test fileName 02");

        FileBox fileBuild = FileBox.builder()
                .fileName(createFileBoxDTO.getFileName())
                .filePath(createFileBoxDTO.getFilePath())
                .fileType(createFileBoxDTO.getFileType())
                .fileOrgName(createFileBoxDTO.getFileOrgName())
                .itemId(Item.builder()
                        .itemId(createFileBoxDTO.getItemId())
                        .build())
                .build();
        FileBox savedFile = fileBoxRepository.save(fileBuild);
        assertThat(fileBuild).isEqualTo(savedFile);
    }

    @Test
    @DisplayName("파일 테이블에서 게시글 번호 존재 유무 확인")
    void existBoardId() {

        Long existsBoardId = 16L;
        Long notExistBoardId = 1L;
        Boolean exists = fileBoxRepository.existsByBoardId(existsBoardId);
        Boolean notExists = fileBoxRepository.existsByBoardId(notExistBoardId);

        assertThat(exists).isTrue();
        assertThat(notExists).isFalse();
    }
}