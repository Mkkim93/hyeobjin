package com.hyeobjin.application.admin.service.file;


import com.hyeobjin.domain.entity.file.FileBox;
import com.hyeobjin.domain.repository.file.FileBoxRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.File;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class AdminInquiryFileService {

    private final FileBoxRepository fileBoxRepository;

    @Value("${file.inquriy.dir}")
    private String fileDir;

    public FileBox findById(Long fileBoxId) {
        return fileBoxRepository.findById(fileBoxId).orElseThrow(() -> new EntityNotFoundException("해당 파일을 찾는 도중 오류가 발생했습니다."));
    }

    public String getFullPath(String fileName) {
        return fileDir + fileName;
    }

    public Boolean deleteStaticFiles(Long inquiryId) {

        List<FileBox> fileBoxes = fileBoxRepository.findAllByInquiryId(inquiryId);

        if (fileBoxes.isEmpty()) {
            return false;
        }

        for (FileBox fileBox : fileBoxes) {

            File file = new File(fileBox.getFilePath());
            if (file.exists()) {
                boolean fileDeleted = file.delete();
                log.info("파일이 성공적으로 삭제 되었습니다. ={}", fileBox.getFileName());
                if (!fileDeleted) {
                    log.error("파일 삭제 오류: ={}", fileBox.getFilePath());
                    throw new RuntimeException("파일 삭제 오류");
                }
            }
        fileBoxRepository.delete(fileBox);
        }
    return true;
    }
}
