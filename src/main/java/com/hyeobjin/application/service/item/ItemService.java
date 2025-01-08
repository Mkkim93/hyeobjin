package com.hyeobjin.application.service.item;

import com.hyeobjin.application.dto.file.CreateFileBoxDTO;
import com.hyeobjin.application.dto.item.CreateItemDTO;
import com.hyeobjin.application.service.file.FileBoxService;
import com.hyeobjin.application.service.manufacturer.ManufacturerService;
import com.hyeobjin.domain.entity.Item;
import com.hyeobjin.domain.repository.ItemRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class ItemService {

    private final ItemRepository itemRepository;
    private final FileBoxService fileBoxService;
    private final ManufacturerService manufacturerService;

    @Transactional
    public void saveItem(CreateItemDTO createItemDTO, List<MultipartFile> files) throws IOException {

        Long manufacturerId = manufacturerService.findIdByManuName(createItemDTO.getMenuName());
        Item item = createItemDTO.toEntity(createItemDTO);
        item.setManufacturerByCreateItem(manufacturerId);

        itemRepository.save(item);

        if (files != null && files.isEmpty()) {
            saveFilesForItem(item, files);
        }
    }

    private void saveFilesForItem(Item item, List<MultipartFile> files) throws IOException {
        CreateFileBoxDTO createFileBoxDTO = new CreateFileBoxDTO();
        createFileBoxDTO.setItemId(item);

        try {
            fileBoxService.fileSave(createFileBoxDTO, files);

        } catch (IOException e) {
            log.info("파일 저장 중 오류 발생: {}", e.getMessage(), e);
            throw e;
        }
    }
}
