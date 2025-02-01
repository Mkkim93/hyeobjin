package com.hyeobjin.application.common.service.item;

import com.hyeobjin.application.common.dto.item.FindByItemDTO;
import com.hyeobjin.domain.repository.item.ItemRepository;
import com.hyeobjin.domain.repository.item.ItemRepositoryImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Slf4j
@Service
@Transactional
public class ItemService {

    private final ItemRepository itemRepository;
    private final ItemRepositoryImpl itemRepositoryImpl;

    @Autowired
    public ItemService(ItemRepository itemRepository, ItemRepositoryImpl itemRepositoryImpl) {
        this.itemRepository = itemRepository;
        this.itemRepositoryImpl = itemRepositoryImpl;
    }

    /**
     * 제품 카테고리별 품번으로 검색
     */
    public FindByItemDTO findByItemOne(FindByItemDTO findByItemDTO) {
        FindByItemDTO result = new FindByItemDTO();
        try {
            result = itemRepositoryImpl.findByItem(findByItemDTO.getManuId() ,findByItemDTO.getItemId());
        } catch (TypeNotPresentException e) {
            e.getMessage();
        }
        log.info("@@@@@@@@@@@@@@@@@@@@@@@result ={}", result.getItemName());
        return result;
    }

    /**
     * 제품의 모든 품번을 조회
     * - 제품별 품번으로 해당 제품의 List Collection 을 조회하기 위해 먼저 현재 존재하는 모든 제품의 품번을 조회한다.
     */
    public List<FindByItemDTO> findAllItemNumList(Long manuId) {
        return itemRepository.findAllItemId(manuId);
    }
}
