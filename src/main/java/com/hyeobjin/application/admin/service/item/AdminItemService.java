package com.hyeobjin.application.admin.service.item;

import com.hyeobjin.application.admin.dto.item.FindAdminItemDTO;
import com.hyeobjin.domain.entity.item.Item;
import com.hyeobjin.domain.repository.item.ItemRepository;
import com.hyeobjin.domain.repository.item.ItemRepositoryImpl;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class AdminItemService {

    private final ItemRepository itemRepository;
    private final ItemRepositoryImpl itemRepositoryImpl;

    public void deleteItemIds(List<Long> itemIds) {

        try {
            List<Item> itemsToDelete = itemRepository.findAllById(itemIds);

            if (itemsToDelete.isEmpty()) {
                throw new RuntimeException("삭제할 제품이 데이터베이스에 존재하지 않습니다.");
            }

            // 제품 삭제
            itemRepository.deleteAllByIdIn(itemIds);
            log.info("삭제된 제품 수 ={}", itemsToDelete.size());

        } catch (Exception e) {

            log.info("제품 삭제 실패 ={}", e.getMessage());
            throw new RuntimeException("제품 삭제 실패", e);
        }
    }

    public Page<FindAdminItemDTO> findAdminItemPages(Pageable pageable, String manuName) {

        return itemRepositoryImpl.findItemList(pageable, manuName);
    }
}
