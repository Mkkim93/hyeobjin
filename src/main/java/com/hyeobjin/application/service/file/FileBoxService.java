package com.hyeobjin.application.service.file;

import com.hyeobjin.application.dto.file.CreateFileBoxDTO;
import com.hyeobjin.domain.entity.FileBox;
import com.hyeobjin.domain.repository.FileBoxRepository;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class FileBoxService {

    @Value("${file.dir}")
    private String fileDir;
    private final FileBoxRepository fileBoxRepository;

    // file download 시 필요
    public String getFullPath(String fileName) {
        return fileDir + fileName;
    }

    /**
     * 파일 생성 시 파일 존재 여부를 확인하고
     * Security 예외를 통해 디렉토리 생성되지 않아 파일 저장 실패 오류에 대해 처리
     */
    @PostConstruct
    public void ensureDirectoryExists() {
        File directory = new File(fileDir);
        if (!directory.exists()) {
            try {
                directory.mkdirs();
            } catch (SecurityException e) {
                throw new IllegalStateException("파일 저장 디렉토리를 생성할 수 없습니다.");
            }
        }
    }

    /**
     * fileSave() : 파일 등록
     * @param createFileBoxDTO 파일 객체
     * @param files
     * @return
     * @throws IOException
     */
    public List<FileBox> fileSave(CreateFileBoxDTO createFileBoxDTO, List<MultipartFile> files) throws IOException {

        List<FileBox> fileBoxes = new ArrayList<>();

        for (MultipartFile file : files) {
            if (file.isEmpty()) {
                continue;
            }

            String filePath = fileDir;
            UUID uuid = UUID.randomUUID();
            String fileName = uuid + "_" + file.getOriginalFilename();
            File saveFile = new File(filePath, fileName);

            file.transferTo(saveFile);

            FileBox savedFiles = FileBox.builder()
                    .fileOrgName(file.getOriginalFilename())
                    .fileName(fileName)
                    .filePath(filePath + fileName)
                    .fileSize(file.getSize())
                    .fileType(file.getContentType())
                    .itemId(createFileBoxDTO.getItemId())
                    .build();

            fileBoxes.add(savedFiles);

            fileBoxRepository.save(savedFiles);
        }
        return fileBoxes;
    }
}
