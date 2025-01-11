package com.hyeobjin.application.service.item;

import com.hyeobjin.application.dto.item.CreateItemDTO;
import com.hyeobjin.application.dto.item.FindByItemDTO;
import com.hyeobjin.application.dto.item.UpdateItemDTO;
import com.hyeobjin.application.service.file.FileBoxService;
import com.hyeobjin.application.service.manufacturer.ManufacturerService;
import com.hyeobjin.domain.entity.item.Item;
import com.hyeobjin.domain.entity.manufacturer.Manufacturer;
import com.hyeobjin.domain.repository.item.ItemRepository;
import com.hyeobjin.domain.repository.item.ItemRepositoryImpl;
import jakarta.persistence.EntityNotFoundException;
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

    /**
     * saveItem() : 제품 등록
     * @param createItemDTO 제품 객체
     * @param files 제품 이미지 & 파일
     * @throws IOException
     */
    public void saveItem(CreateItemDTO createItemDTO, List<MultipartFile> files) throws IOException {

        Manufacturer manufacturer = manufacturerService.findIdByManuName(createItemDTO.getMenuName());
        Item item = createItemDTO.toEntity(createItemDTO);
        item.setManufacturerByCreateItem(manufacturer.getId());

        itemRepository.save(item);

        if (files != null && !files.isEmpty()) {
            fileBoxService.saveFilesForItem(item, files);
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

    /**
     * 제품 수정
     * - 수정할 제품의 객체를 id 로 조회 - 클라이언트에서 입력한 데이터가 존재하면 수정하고 존재하지 않으면 기존 데이터 유지
     */
    public void update(UpdateItemDTO updateItemDTO, List<MultipartFile> files) throws IOException {

        Item updateItem = itemRepository.findById(updateItemDTO.getItemId())
                .orElseThrow(() -> new EntityNotFoundException("해당 제품이 존재하지 않습니다."));

        UpdateItemDTO updatedItemDTO = itemRepositoryImpl.updateItem(updateItemDTO);

        updateItem.setItemIdFromDto(updatedItemDTO);

        if (files != null && !files.isEmpty()) {
            fileBoxService.saveFilesForItem(updateItem, files);
        }
    }
}
