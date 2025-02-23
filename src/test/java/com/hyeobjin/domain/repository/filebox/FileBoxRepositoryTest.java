package com.hyeobjin.domain.repository.filebox;

import com.hyeobjin.domain.entity.file.FileBox;
import com.hyeobjin.domain.repository.file.FileBoxRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;


@Slf4j
@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@DisplayName("파일 테스트")
class FileBoxRepositoryTest {

    @Autowired
    private FileBoxRepository fileBoxRepository;

    @Autowired
    private EntityManager entityManager;

    @Test
    @DisplayName("파일 메타데이터 저장 테스트")
    void saveFileMetadata() {
        // given
        FileBox fileBox = FileBox.builder()
                .fileName("test fileName 02")
                .filePath("test filePath 02")
                .fileType("image/jpeg")
                .fileOrgName("test_original_filename.jpg")
                .build();

        // when
        FileBox savedFile = fileBoxRepository.save(fileBox);
        entityManager.flush();
        entityManager.clear();

        // then (DB에서 다시 조회하여 검증)
        FileBox foundFile = fileBoxRepository.findById(savedFile.getId())
                .orElseThrow(() -> new EntityNotFoundException("파일이 저장되지 않았습니다."));

        assertThat(foundFile).isNotNull();
        assertThat(foundFile.getFileName()).isEqualTo("test fileName 02");
        assertThat(foundFile.getFilePath()).isEqualTo("test filePath 02");
        assertThat(foundFile.getFileType()).isEqualTo("image/jpeg");
        assertThat(foundFile.getFileOrgName()).isEqualTo("test_original_filename.jpg");
    }

    @Test
    @DisplayName("조회 : 게시글 파일 PK 조회 시 TRUE/FALSE")
    void existBoardId() {

        // given
        Long existsBoardId = 1L;
        Long notExistBoardId = 3L;

        // when
        Boolean exists = fileBoxRepository.existsByBoardId(existsBoardId);
        Boolean notExists = fileBoxRepository.existsByBoardId(notExistBoardId);

        // then
        assertThat(exists).isTrue();
        assertThat(notExists).isFalse();
    }

    @Test
    @DisplayName("게시글에서 삭제할 파일 조회 (게시글 ID : boardId, 파일 ID : fileboxId 로 filebox 엔티티 조회)")
    void findByDeleteFileBoxEntity() {

        // given
        Long fileBoxId = 43L;
        Long boardId = 42L;

        // when
        FileBox fileBox = fileBoxRepository.findByBoardIdAndId(boardId, fileBoxId);

        // then
        System.out.println("fileBox = " + fileBox);
    }

    @Test
    @DisplayName("현재 게시글 id 를 기준으로 파일 pk를 list 로 모두 조회")
    void findByDeleteFileBoxIdForBoardId() {

        // given
        List<Long> boardIds = new ArrayList<>();
        boardIds.add(1L); // file size = 1

        // when
        List<Long> deletedFileBoxIds = fileBoxRepository.findFileBoxIdsByBoardIdIn(boardIds);

        // then
        assertThat(deletedFileBoxIds.size()).isEqualTo(3);
    }

    @Test
    @DisplayName("조회 : 원본 파일명으로 해당 객체 조회")
    void findFileNameToEntity() {

        // given
        String findFileOrgName = "공지파일1.png";

        // when
        FileBox byFileName = fileBoxRepository.findByFileOrgName(findFileOrgName);

        // then
        assertThat(findFileOrgName).isEqualTo(byFileName.getFileOrgName());
    }

}