package com.hyeobjin.domain.repository;

import com.hyeobjin.application.dto.file.CreateFileBoxDTO;
import com.hyeobjin.domain.entity.FileBox;
import com.hyeobjin.domain.entity.Item;
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

    @Autowired FileBoxRepository fileBoxRepository;

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
                        .itemId(1L)
                .build());
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
                        .itemId(createFileBoxDTO.getItemId().getId())
                        .build().getId())
                .build();
        FileBox savedFile = fileBoxRepository.save(fileBuild);
        assertThat(fileBuild).isEqualTo(savedFile);
    }
}