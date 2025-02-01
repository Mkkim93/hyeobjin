package com.hyeobjin.domain.repository;

import com.hyeobjin.application.admin.dto.file.UpdateItemFileDTO;
import com.hyeobjin.domain.entity.file.FileBox;
import com.hyeobjin.domain.entity.item.Item;
import com.hyeobjin.domain.repository.file.FileBoxRepository;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;


import java.util.ArrayList;
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
        UpdateItemFileDTO createFileBoxDTO = new UpdateItemFileDTO();
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
    @DisplayName("파일 테이블에서 게시글 번호 유무 확인")
    void existBoardId() {

        Long existsBoardId = 16L;
        Long notExistBoardId = 1L;
        Boolean exists = fileBoxRepository.existsByBoardId(existsBoardId);
        Boolean notExists = fileBoxRepository.existsByBoardId(notExistBoardId);

        assertThat(exists).isTrue();
        assertThat(notExists).isFalse();
    }

    @Test
    @DisplayName("게시글에서 삭제할 파일 조회 (게시글 ID : boardId, 파일 ID : fileboxId 로 filebox 엔티티 조회)")
    void findByDeleteFileBoxEntity() {
        Long fileBoxId = 43L;
        Long boardId = 42L;
        FileBox fileBox = fileBoxRepository.findByBoardIdAndId(boardId, fileBoxId);

        System.out.println("fileBox = " + fileBox);
    }

    @Test
    void findByDeleteFileBoxIdForItemId() {
        Long id = fileBoxRepository.findByDeleteFileBoxId(15L);
        System.out.println("id = " + id); // 10
    }

    @Test
    void exist() {
        Boolean exists = fileBoxRepository.existsByIsMain(3L);
        System.out.println("exists = " + exists);
    }

    @Test
    @DisplayName("현재 게시글 id 를 기준으로 파일 pk를 list 로 모두 조회")
    void findByDeleteFileBoxIdForBoardId() {
        List<Long> boardIds = new ArrayList<>();

        boardIds.add(9L); // file size = 1
        boardIds.add(10L); // file size = 1
        boardIds.add(11L); // file size = 1
        boardIds.add(12L); // file size = 4
        // total size = 7

        List<Long> deletedFileBoxIds = fileBoxRepository.findFileBoxIdsByBoardIdIn(boardIds);

        assertThat(deletedFileBoxIds.size()).isEqualTo(7);
    }

    @Test
    void findFileNameToEntity() {
        FileBox byFileName = fileBoxRepository.findByFileOrgName("서브수정1.jpg");
        System.out.println("byFileName.getId() = " + byFileName.getId());
        System.out.println("byFileName = " + byFileName.getFileName());
        System.out.println("byFileName.getFileOrgName() = " + byFileName.getFileOrgName());
    }

}