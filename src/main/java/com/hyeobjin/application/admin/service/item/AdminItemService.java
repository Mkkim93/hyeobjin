package com.hyeobjin.application.admin.service.item;

import com.hyeobjin.application.admin.dto.item.FindAdminDetailDTO;
import com.hyeobjin.application.admin.dto.item.FindAdminItemDTO;
import com.hyeobjin.application.admin.service.file.AdminFileService;
import com.hyeobjin.application.admin.service.fix.AdminItemManuService;
import com.hyeobjin.application.common.dto.file.UpdateItemFileDTO;
import com.hyeobjin.application.common.dto.item.CreateItemDTO;
import com.hyeobjin.application.common.dto.item.FindByItemDTO;
import com.hyeobjin.application.common.dto.item.UpdateItemDTO;
import com.hyeobjin.application.common.service.file.FileBoxService;
import com.hyeobjin.domain.entity.item.Item;
import com.hyeobjin.domain.entity.manufacturer.Manufacturer;
import com.hyeobjin.domain.repository.item.ItemRepository;
import com.hyeobjin.domain.repository.item.ItemRepositoryImpl;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.orm.ObjectOptimisticLockingFailureException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.concurrent.CompletableFuture;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class AdminItemService {

    private final FileBoxService fileBoxService;
    private final AdminFileService adminFileService;
    private final AdminItemManuService adminItemManuService;
    private final ItemRepository itemRepository;
    private final ItemRepositoryImpl itemRepositoryImpl;

    public void deleteItemIds(List<Long> itemIds) {

        try {
            List<Item> itemsToDelete = itemRepository.findAllById(itemIds); // 삭제할 아이디 존재 확인

            if (itemsToDelete.isEmpty()) {
                throw new RuntimeException("삭제할 제품이 데이터베이스에 존재하지 않습니다.");
            }

            CompletableFuture<Boolean> fileDeletion = CompletableFuture.supplyAsync(() -> {
                return adminFileService.deleteFiles(itemIds);
            });

            Boolean haseFiles = fileDeletion.join();

            if (!haseFiles) {
                itemRepository.deleteAllByIdIn(itemIds);
                log.info("파일이 없느느 제품 삭제, 로직 종료");
                return;
            }

            // 제품 삭제
            itemRepository.deleteAllByIdIn(itemIds);
            log.info("삭제된 제품 수 ={}", itemsToDelete.size());

        } catch (ObjectOptimisticLockingFailureException e) {
            log.info("ObjectOptimisticLockingFailureException ={}", e);
        }
    }



    public Page<FindAdminItemDTO> findAdminItemPages(Pageable pageable, String manuName) {

        return itemRepositoryImpl.findItemList(pageable, manuName);
    }

    /**
     * 관리자가 제품 상세 페이지 (제품 수정을 위한 페이지)
     */
    public FindAdminDetailDTO findByItemDetail(FindByItemDTO findAdminItemDTO) {

        return itemRepositoryImpl.findItemDetail(findAdminItemDTO.getManuId(), findAdminItemDTO.getItemId());
    }

    public FindAdminDetailDTO findByItemId(Long itemId) {
        Item item = itemRepository.findById(itemId).orElseThrow(() -> new EntityNotFoundException("제품의 pk 가 존재하지 않습니다."));

        return itemRepositoryImpl.findItemDetail(item.getManufacturer().getId(), item.getId());
    }

    /**
     * saveItem() : 제품 등록
     * @param createItemDTO 제품 객체
     * @param files 제품 이미지 & 파일
     * @throws IOException
     */
    public void saveItem(CreateItemDTO createItemDTO, List<MultipartFile> files) throws IOException {

        Manufacturer manufacturer = adminItemManuService.findIdByManuName(createItemDTO.getManuName());

        Item item = createItemDTO.toEntity(createItemDTO);
        item.setManufacturerByCreateItem(manufacturer.getId());

        Item savedItem = itemRepository.save(item);

        if (files != null && !files.isEmpty()) {
            fileBoxService.saveFilesForItem(savedItem, files, createItemDTO.getIsMain());
        }
    }

    /**
     * 제품 수정
     * - 수정할 제품의 객체를 id 로 조회 - 클라이언트에서 입력한 데이터가 존재하면 수정하고 존재하지 않으면 기존 데이터 유지
     */
    public void update(UpdateItemDTO updateItemDTO, List<MultipartFile> files) throws IOException {

        itemRepository.findById(updateItemDTO.getItemId())
                .orElseThrow(() -> new EntityNotFoundException("해당 제품이 존재하지 않습니다."));

        UpdateItemDTO updateComplete = itemRepositoryImpl.updateItem(updateItemDTO);

        UpdateItemFileDTO updateItemFileDTO = new UpdateItemFileDTO();
        updateItemFileDTO.setItemId(updateComplete.getItemId());
        updateItemFileDTO.setFileBoxId(updateComplete.getFileBoxId());

        if (files != null && !files.isEmpty()) {
            // TODO
            fileBoxService.updateFilesForItem(updateItemFileDTO, files);
        }
    }

    /**
     * 제품 삭제
     * - 삭제할 제품의 객체를 id 로 조회하고 조회한 객체가 존재하면 itemYN 값을 N -> Y 로 변경하여 사용자가 볼 수 없도록 한다.
     */
    public void delete(Long itemId) {
        Item item = itemRepository.findById(itemId)
                .orElseThrow(() -> new EntityNotFoundException("해당 제품의 정보를 불러오는데 실패하였습니다."));

        Integer deleteCount = itemRepository.updateItemYN(item.getId());

        if (deleteCount != 1) {
            throw new RuntimeException("제품 삭제에 실패하였습니다");
        }

        log.info("제품이 성공적으로 삭제 되었습니다.");
    }

}
