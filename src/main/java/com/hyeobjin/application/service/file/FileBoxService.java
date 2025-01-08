package com.hyeobjin.application.service.file;

import com.hyeobjin.application.dto.file.CreateFileBoxDTO;
import com.hyeobjin.domain.entity.FileBox;
import com.hyeobjin.domain.entity.Item;
import com.hyeobjin.domain.repository.FileBoxRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
@Transactional
@RequiredArgsConstructor
public class FileBoxService {

    @Value("${file.dir}")
    private String fileDir;
    private final FileBoxRepository fileBoxRepository;

    // file download 시 필요
    public String getFullPath(String fileName) {
        return fileDir + fileName;
    }

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
                    .itemId(createFileBoxDTO.getItemId().getId())
                    .build();

            fileBoxes.add(savedFiles);

            fileBoxRepository.save(savedFiles);
        }
        return fileBoxes;
    }
}
