package com.hyeobjin.application.service.item;

import com.hyeobjin.application.dto.file.CreateFileBoxDTO;
import com.hyeobjin.application.dto.item.CreateItemDTO;
import com.hyeobjin.application.dto.item.FindByItemDTO;
import com.hyeobjin.application.service.file.FileBoxService;
import com.hyeobjin.application.service.manufacturer.ManufacturerService;
import com.hyeobjin.domain.entity.Item;
import com.hyeobjin.domain.repository.ItemRepository;
import com.hyeobjin.domain.repository.ItemRepositoryImpl;
import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class ItemService {

    private final ItemRepository itemRepository;
    private final ItemRepositoryImpl itemRepositoryImpl;
    private final FileBoxService fileBoxService;
    private final ManufacturerService manufacturerService;
    private final EntityManager em;

    /**
     * saveItem() : 제품 등록
     * @param createItemDTO 제품 객체
     * @param files 제품 이미지 & 파일
     * @throws IOException
     */

    public void saveItem(CreateItemDTO createItemDTO, List<MultipartFile> files) throws IOException {

        Long manufacturerId = manufacturerService.findIdByManuName(createItemDTO.getMenuName());
        Item item = createItemDTO.toEntity(createItemDTO);
        item.setManufacturerByCreateItem(manufacturerId);

        itemRepository.save(item);

        if (files != null && !files.isEmpty()) {
            saveFilesForItem(item, files);
        }
    }

    /**
     * saveFilesForItem() : 파일 등록
     * @param item 제품 id
     * @param files 파일 객체
     * @throws IOException
     */
    private void saveFilesForItem(Item item, List<MultipartFile> files) throws IOException {
        CreateFileBoxDTO createFileBoxDTO = new CreateFileBoxDTO();
        createFileBoxDTO.setItemId(item.getId());

        try {
            fileBoxService.fileSave(createFileBoxDTO, files);

        } catch (IOException e) {
            log.info("파일 저장 중 오류 발생: {}", e.getMessage(), e);
            throw e;
        }
    }

    /**
     * 제품 카테고리별 품번으로 검색
     */
    public FindByItemDTO findByItemOne(FindByItemDTO findByItemDTO) {
        FindByItemDTO result = new FindByItemDTO();
        try {
            result = itemRepositoryImpl.findByItem(findByItemDTO.getItemNum());
        } catch (TypeNotPresentException e) {
            e.getMessage();
        }
        return result;
    }
}
