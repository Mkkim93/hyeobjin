package com.hyeobjin.application.service.file;

import com.hyeobjin.application.dto.file.CreateFileBoxDTO;
import com.hyeobjin.domain.entity.Item;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@Slf4j
@SpringBootTest
@Transactional
class FileBoxServiceTest {

    @Autowired FileBoxService fileBoxService;

    @Test
    @DisplayName("file save test")
    void save() throws IOException {
        CreateFileBoxDTO createFileBoxDTO = new CreateFileBoxDTO();
        List<MultipartFile> files = new ArrayList<>();
        createFileBoxDTO.setItemId(Item.builder()
                        .itemId(1L)
                .build());
        fileBoxService.fileSave(createFileBoxDTO, files);
    }
}