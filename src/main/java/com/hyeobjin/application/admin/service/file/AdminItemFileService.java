package com.hyeobjin.application.admin.service.file;

import com.hyeobjin.application.admin.dto.file.UpdateItemFileDTO;
import com.hyeobjin.application.admin.dto.item.UpdateItemDTO;
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
public class AdminItemFileService {

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
    public void saveFilesForItem(Item item, List<MultipartFile> files) throws IOException {

        UpdateItemFileDTO createFileBoxDTO = new UpdateItemFileDTO();
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

        if (!file.exists()) {
            log.warn("파일이 존재하지 않아 삭제할 수 없습니다.");
            return false; // 파일이 없으면 false 반환
        }

        boolean fileDeleted = file.delete();
        if (!fileDeleted) {
            throw new RuntimeException("파일 삭제 오류");
        }

        log.info("파일이 성공적으로 삭제되었습니다.");
        return true;
    }

    public void fileAddSave(UpdateItemFileDTO updateItemFileDTO, List<MultipartFile> files) throws IOException {

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

            String targetPath = updateItemFileDTO.getIsMain() ? filePath : filePathSub; // 저장할 경로 선택

            File saveFile = new File(targetPath, fileName);

            file.transferTo(saveFile);

            FileBox savedFiles = FileBox.builder()
                    .id(updateItemFileDTO.getFileBoxId())
                    .fileOrgName(file.getOriginalFilename())
                    .fileName(fileName)
                    .filePath(targetPath + fileName)
                    .fileSize(file.getSize())
                    .fileType(file.getContentType())
                    .isMain(updateItemFileDTO.getIsMain()) // 인덱스에 따라 isMain 설정
                    .fileRegDate(LocalDateTime.now())
                    .itemId(Item.builder()
                            .itemId(updateItemFileDTO.getItemId())
                            .build())
                    .build();

            fileBoxes.add(savedFiles);

            fileBoxRepository.save(savedFiles);
        }
    }

    public void deleteFileFirst(Long fileBoxId) {

        FileBox fileBox = fileBoxRepository.findById(fileBoxId).orElseThrow(() -> new EntityNotFoundException("해당 파일이 존재 하지 않습니다."));

        CompletableFuture<Boolean> hasDeleted = CompletableFuture.supplyAsync(() -> {
            return deleteFile(fileBoxId);
        });

        Boolean hasDeleteFile = hasDeleted.join();

        if (!hasDeleteFile) {
            log.info("파일 삭제 중 오류 발생");
            return;
        }
        fileBoxRepository.delete(fileBox);
    }

    @Transactional
    public String findFileBoxIds(UpdateItemDTO updateItemDTO, MultipartFile mainFile) throws IOException {
        // ✅ 1. 파일 박스 ID가 없는 경우 새로운 파일 박스 생성 후 저장
        if (updateItemDTO.getFileBoxId() == null) {
            FileBox newFileBox = new FileBox();
            newFileBox.setItemId(updateItemDTO.getItemId());
            // ✅ 새로운 파일 저장 후 즉시 반환
            updateNewMainFile(newFileBox, mainFile);
            return "새로운 파일 저장 완료";
        }

        // ✅ 2. 기존 파일이 존재하는 경우 파일을 삭제 후 업데이트
        FileBox fileBox = fileBoxRepository.findById(updateItemDTO.getFileBoxId())
                .orElseThrow(() -> new EntityNotFoundException("파일 찾는 도중 오류 발생"));

        // ✅ 기존 파일 삭제
        deleteFile(fileBox.getId());

        // ✅ 기존 FileBox 객체에 새로운 파일 정보 업데이트
        updateMainFile(fileBox, mainFile);

        return "기존 파일 업데이트 완료";
    }


    private void updateMainFile(FileBox fileBox, MultipartFile mainFile) throws IOException {

        String filePath = fileDir;

        UUID uuid = UUID.randomUUID();
        String fileName = uuid + "_" + mainFile.getOriginalFilename();


        File saveFile = new File(filePath, fileName);

        mainFile.transferTo(saveFile);

        FileBox savedFile = FileBox.builder()
                .id(fileBox.getId())
                .fileOrgName(mainFile.getOriginalFilename())
                .fileName(fileName)
                .filePath(filePath + fileName)
                .fileSize(mainFile.getSize())
                .fileType(mainFile.getContentType())
                .isMain(true)
                .fileRegDate(LocalDateTime.now())
                .itemId(Item.builder()
                        .itemId(fileBox.getItem().getId())
                        .build())
                .build();

        fileBoxRepository.save(savedFile);
    }

    private void updateNewMainFile(FileBox fileBox, MultipartFile mainFile) throws IOException {

        String filePath = fileDir;

        UUID uuid = UUID.randomUUID();
        String fileName = uuid + "_" + mainFile.getOriginalFilename();


        File saveFile = new File(filePath, fileName);

        mainFile.transferTo(saveFile);

        FileBox savedFile = FileBox.builder()
                .fileOrgName(mainFile.getOriginalFilename())
                .fileName(fileName)
                .filePath(filePath + fileName)
                .fileSize(mainFile.getSize())
                .fileType(mainFile.getContentType())
                .isMain(true)
                .fileRegDate(LocalDateTime.now())
                .itemId(Item.builder()
                        .itemId(fileBox.getItem().getId())
                        .build())
                .build();

        fileBoxRepository.save(savedFile);
    }
}
