package com.hyeobjin.application.service.file;

import com.hyeobjin.application.dto.file.CreateFileBoxDTO;
import com.hyeobjin.domain.entity.file.FileBox;
import com.hyeobjin.domain.entity.item.Item;
import com.hyeobjin.domain.repository.file.FileBoxRepository;
import jakarta.annotation.PostConstruct;
import jakarta.persistence.EntityNotFoundException;
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
public class FileBoxService {

    @Value("${file.dir}")
    private String fileDir;
    private final FileBoxRepository fileBoxRepository;

    // file download 시 필요
    public String getFullPath(String fileName) {
        return fileDir + fileName;
    }

    /**
     * 클라이언트 요청 데이터 fileBoxId 로 해당 객체 반환
     * - file download 사용
     * @param fileBoxId
     * @return
     */
    public FileBox findById(Long fileBoxId) {
        return fileBoxRepository.findById(fileBoxId)
                .orElseThrow(() -> new EntityNotFoundException("해당 파일이 존재하지 않습니다."));
    }

    /**
     * 파일 생성 시 파일 존재 여부를 확인하고
     * Security 예외를 통해 디렉토리 생성되지 않을 시, 파일 저장 실패 오류에 대해 처리
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

    public void saveFilesForItem(Item item, List<MultipartFile> files) throws IOException {

        CreateFileBoxDTO createFileBoxDTO = new CreateFileBoxDTO();
        createFileBoxDTO.setItemId(item.getId());

        try {
            fileSave(createFileBoxDTO, files);

        } catch (IOException e) {
            log.info("파일 저장 중 오류 발생: {}", e.getMessage(), e);
            throw e;
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

    /**
     * 파일 삭제 (정적경로의 파일 우선 삭제 후 DB 메타데이터 삭제)
     * RuntimeException : 정적 파일 삭제 실패 시, 런타임 예외 발생 메타데이터 삭제 로직이 실행 되지 않도록 한다.
     * @param fileBoxId
     */
    public void deleteFile(Long fileBoxId) {

        FileBox fileBox = fileBoxRepository.findById(fileBoxId)
                .orElseThrow(() -> new EntityNotFoundException("해당 파일이 존재하지 않습니다."));

        File file = new File(fileBox.getFilePath());
        // 정적경로 파일 삭제
        if (file.exists()) {
            boolean fileDeleted = file.delete();
            log.info("파일이 성공적으로 삭제 되었습니다.");
            if (!fileDeleted) {
                throw new RuntimeException("파일 삭제 오류");
            }
        }
        fileBoxRepository.delete(fileBox);
    }
}
