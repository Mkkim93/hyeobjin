package com.hyeobjin.application.common.service.file;

import com.hyeobjin.application.common.dto.file.UpdateItemFileDTO;
import com.hyeobjin.domain.entity.file.FileBox;
import com.hyeobjin.domain.entity.item.Item;
import com.hyeobjin.domain.repository.file.FileBoxRepository;
import jakarta.annotation.PostConstruct;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class FileBoxService {

    @Value("${file.item.dir}")
    private String fileDir;

    @Value("${file.itemSub.dir}")
    private String fileDirSub;

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
        log.info("item fileDir ={}", fileDir);
        if (!directory.exists()) {
            try {
                directory.mkdirs();
            } catch (SecurityException e) {
                throw new IllegalStateException("제품 파일 저장 디렉토리를 생성할 수 없습니다.");
            }
        }
    }

    // MultipartFile 의 메타데이터 저장 : file -> dto
    public void saveFilesForItem(Item item, List<MultipartFile> files, Boolean isMain) throws IOException {

        UpdateItemFileDTO createFileBoxDTO = new UpdateItemFileDTO();
        createFileBoxDTO.setItemId(item.getId());
        createFileBoxDTO.setIsMain(isMain);

        try {
            fileSave(createFileBoxDTO, files);

            } catch (IOException e) {
                log.info("파일 저장 중 오류 발생: {}", e.getMessage(), e);
                throw e;
        }
    }


    public void updateFilesForItem(UpdateItemFileDTO updateItemFileDTO, List<MultipartFile> files) {

        try {
            if (files != null && !files.isEmpty()) {
                Long deleteById = fileBoxRepository.findByDeleteFileBoxId(updateItemFileDTO.getItemId());
                if (deleteById != null) {
                    boolean deleted = deleteFile(deleteById); // 동기적으로 실행
                    if (deleted) {
                        fileSave(updateItemFileDTO, files);
                    } else {
                        throw new RuntimeException("파일 삭제 실패로 인해 새로운 파일을 저장할 수 없습니다.");
                    }
                } else {
                    fileSave(updateItemFileDTO, files);
                }
            } else {
                log.info("파일이 제공되지 않았습니다. 기존 파일을 유지합니다.");
            }
        } catch (IOException e) {
            log.error("파일 저장 중 오류 발생: {}", e.getMessage(), e);
            throw new RuntimeException("파일 저장 오류");
        }
    }

    /**
     * fileSave() : 파일 등록
     * @param updateItemFileDTO 파일 객체
     * @param files
     * @return
     * @throws IOException
     */
    public void fileSave(UpdateItemFileDTO updateItemFileDTO, List<MultipartFile> files) throws IOException {

        List<FileBox> fileBoxes = new ArrayList<>();

        for (int i = 0; i < files.size(); i++) {
            MultipartFile file = files.get(i);
            if (file.isEmpty()) {
                continue;
            }

            String filePath = fileDir;
            String filePathSub = fileDirSub; // TODO 저장할 경로 나눠야될듯

            UUID uuid = UUID.randomUUID();
            String fileName = uuid + "_" + file.getOriginalFilename();

            // 첫 번째 파일 (인덱스 0)은 isMain = TRUE, 나머지 파일은 isMain = FALSE
            boolean isMain = (i == 0); // i가 0이면 isMain = TRUE, 그렇지 않으면 FALSE

            String targetPath = isMain ? filePath : filePathSub; // 저장할 경로 선택

            File saveFile = new File(targetPath, fileName);

            file.transferTo(saveFile);
            FileBox savedFiles = FileBox.builder()
                    .id(updateItemFileDTO.getFileBoxId())
                    .fileOrgName(file.getOriginalFilename())
                    .fileName(fileName)
                    .filePath(targetPath + fileName)
                    .fileSize(file.getSize())
                    .fileType(file.getContentType())
                    .isMain(isMain) // 인덱스에 따라 isMain 설정
                    .fileRegDate(LocalDateTime.now())
                    .itemId(Item.builder()
                            .itemId(updateItemFileDTO.getItemId())
                            .build())
                    .build();

            fileBoxes.add(savedFiles);

            fileBoxRepository.save(savedFiles);
        }
    }

    /**
     * 현재 제품번호를 조회하고 제품이 존재하면 새로운 파일을 추가
     * @param itemId 제품 번호
     * @param files 파일 데이터
     * @throws IOException
     */
    public void saveFileOnly(Long itemId, List<MultipartFile> files) throws IOException {
        fileSave(new UpdateItemFileDTO(itemId), files);
    }

    /**
     * 파일 삭제 (정적경로의 파일 우선 삭제 후 DB 메타데이터 삭제)
     * RuntimeException : 정적 파일 삭제 실패 시, 런타임 예외 발생 메타데이터 삭제 로직이 실행 되지 않도록 한다.
     * @param fileBoxId
     */
    public boolean deleteFile(Long fileBoxId) {
        FileBox fileBox = fileBoxRepository.findById(fileBoxId)
                .orElseThrow(() -> new EntityNotFoundException("해당 파일이 존재하지 않습니다."));
        File file = new File(fileBox.getFilePath());
        log.info("file ={}", file);
        log.info("file.getAbsolutePath ={}", file.getAbsoluteFile());
        if (file.exists()) {
            boolean fileDeleted = file.delete();
            if (!fileDeleted) {
                throw new RuntimeException("파일 삭제 오류");
            }
            log.info("파일이 성공적으로 삭제되었습니다.");
        } else {
            log.warn("파일이 존재하지 않아 삭제할 수 없습니다.");
        }
        return true;
    }
}
