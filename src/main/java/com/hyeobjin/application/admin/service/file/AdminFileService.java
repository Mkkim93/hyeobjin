package com.hyeobjin.application.admin.service.file;

import com.hyeobjin.domain.entity.file.FileBox;
import com.hyeobjin.domain.repository.file.FileBoxRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.io.File;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class AdminFileService {

    private final FileBoxRepository fileBoxRepository;

    public boolean deleteFiles(List<Long> itemIds) {
        // fileBoxIds에 해당하는 모든 FileBox 객체를 조회
        List<Long> fileBoxIds = fileBoxRepository.findFileBoxIdsByItemIdIn(itemIds);

        List<FileBox> fileBoxes = fileBoxRepository.findAllById(fileBoxIds);

        if (fileBoxes.isEmpty()) {
            return false;
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
        return true;
    }
}
