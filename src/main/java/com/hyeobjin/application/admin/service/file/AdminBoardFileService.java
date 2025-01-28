package com.hyeobjin.application.admin.service.file;

import com.hyeobjin.domain.entity.file.FileBox;
import com.hyeobjin.domain.repository.file.FileBoxRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.io.File;
import java.util.List;

/**
 * 게시글과 연관된 작업을 위한 별도의 FileBox 서비스 레이어
 * 주로 게시글을 삭제하거나 업데이트 하기 위한 레이어
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class AdminBoardFileService {

    private final FileBoxRepository fileBoxRepository;

    public void deleteByStaticFiles(List<Long> boardIds) {

        List<Long> deleteFileBoxIds = fileBoxRepository.findFileBoxIdsByBoardIdIn(boardIds);

        List<FileBox> fileBoxes = fileBoxRepository.findAllById(deleteFileBoxIds);

        if (fileBoxes.isEmpty()) {
            throw new EntityNotFoundException("해당 파일들이 존재하지 않습니다.");
        }

        for (FileBox fileBox : fileBoxes) {
            // 파일 경로로 File 객체 생성
            File file = new File(fileBox.getFilePath());

            // 정적 경로에서 파일 삭제
            if (file.exists()) {
                boolean fileDeleted = file.delete();
                log.info("파일이 성공적으로 삭제되었습니다: " + fileBox.getFilePath());
                if (!fileDeleted) {
                    log.error("파일 삭제 오류: " + fileBox.getFilePath());
                    throw new RuntimeException("파일 삭제 오류");
                }
            }

            // FileBox 레코드 삭제
            fileBoxRepository.delete(fileBox);
        }
    }
}
