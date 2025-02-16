package com.hyeobjin.application.common.service.inquiry.file;

import com.hyeobjin.domain.entity.file.FileBox;
import com.hyeobjin.domain.entity.inquiry.Inquiry;
import com.hyeobjin.domain.repository.file.FileBoxRepository;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class InquiryFileService {

    @Value("${file.inquriy.dir}")
    private String fileDir;
    private final FileBoxRepository fileBoxRepository;

    public String getFullPath(String fileName) {
        return fileDir + fileName;
    }

    @PostConstruct
    public void ensureDirectoryExists() {
        File directory = new File(fileDir);
        log.info("board fileDir ={}", fileDir);
        if (!directory.exists()) {
            try {
                directory.mkdirs();
            } catch (SecurityException e) {
                throw new IllegalStateException("게시판 파일 저장 디렉토리를 찾을 수 없습니다.");
            }
        }
    }

    public void fileSave(Inquiry inquiryId, List<MultipartFile> files) throws IOException {

        List<FileBox> fileBoxes = new ArrayList<>();
        List<String> storedFiles = new ArrayList<>();

        try {
            for (MultipartFile file : files) {
                if (file.isEmpty()) continue;

                String filePath = fileDir;
                UUID uuid = UUID.randomUUID();
                String fileName = uuid + "_" + file.getOriginalFilename();
                File saveFile = new File(getFullPath(fileName));

                file.transferTo(saveFile);
                storedFiles.add(saveFile.getAbsolutePath());

                FileBox savedFiles = FileBox.builder()
                        .fileOrgName(file.getOriginalFilename())
                        .fileName(fileName)
                        .filePath(filePath + fileName)
                        .fileSize(file.getSize())
                        .fileType(file.getContentType())
                        .inquiryId(inquiryId) // ✅ 영속성 컨텍스트에 포함된 Inquiry 사용
                        .build();

                fileBoxes.add(savedFiles);
            }

            fileBoxRepository.saveAll(fileBoxes); // ✅ 배치 저장으로 성능 향상

        } catch (Exception e) {
            // 예외 발생 시 저장된 파일 삭제
            for (String filePath : storedFiles) {
                new File(filePath).delete();
            }
            throw new RuntimeException("파일 저장 중 오류 발생", e);
        }
    }

}
