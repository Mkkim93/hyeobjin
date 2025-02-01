package com.hyeobjin.domain.repository;

import com.hyeobjin.application.admin.dto.item.CreateItemDTO;
import com.hyeobjin.application.admin.dto.item.FindAdminDetailDTO;
import com.hyeobjin.application.common.dto.item.FindByItemDTO;
import com.hyeobjin.domain.entity.item.Item;
import com.hyeobjin.domain.repository.item.ItemRepository;
import com.hyeobjin.domain.repository.item.ItemRepositoryImpl;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.PageRequest;

import java.util.List;

import static org.assertj.core.api.Assertions.*;

@Slf4j
@SpringBootTest
class ItemRepositoryTest {

    @Autowired
    ItemRepository itemRepository;
    @Autowired
    ItemRepositoryImpl itemRepositoryImpl;

    @Test
    @DisplayName("ItemRepository connection test")
    void findAll() {
        List<Item> result = itemRepository.findAll();
        result.stream().forEach(System.out::println);
        assertThat(result).isNull();
    }

    @Test
    @DisplayName("item save test")
    void save() {
        CreateItemDTO createItemDTO = new CreateItemDTO();
        createItemDTO.setItemName("test ItemName02");
        createItemDTO.setItemSpec("test ItemSpec02");
        createItemDTO.setItemUse("test ItemUse02");
        createItemDTO.setItemType("door");
        createItemDTO.setItemYN("N");
        createItemDTO.setItemDescription("test ItemDescription test");

        Item savedBeforeItem = createItemDTO.toEntity(createItemDTO);
        System.out.println("savedBeforeItem.getItemDescription() = " + savedBeforeItem.getItemDescription());

        Item savedAfterItem = itemRepository.save(savedBeforeItem);
        System.out.println("savedAfterItem.getItemDescription() = " + savedAfterItem.getItemDescription());

        assertThat(savedBeforeItem).isEqualTo(savedAfterItem);
    }

    @Test
    @DisplayName("제품 소개 카테고리에서 품번으로 조회")
    void findDetailItem() {

        FindByItemDTO findByItemDTO = new FindByItemDTO();
        findByItemDTO.setItemNum("K102");

        FindByItemDTO byItem = (FindByItemDTO) itemRepositoryImpl.findByItem(findByItemDTO.getManuId(),
                findByItemDTO.getItemId());

        System.out.println("findOneByItem = " + byItem);
    }

    @Test
    @DisplayName("특정 제조사번호를 입력 후, 제품의 모든 품번을 조회")
    void findAllItemId() {
        Long manuId = 1L;
        itemRepository.findAllItemId(manuId).stream().forEach(System.out::println);
    }

    @Test
    @DisplayName("관리자 제품 조회 (페이징)")
    void findItemAllPage() {
        PageRequest pageRequest = PageRequest.of(0, 5);


        itemRepositoryImpl.findItemList(pageRequest, "KCC");
    }

    @Test
    @DisplayName("관리자 페이지 상세 조회")
    void findByItemDetail() {

        FindAdminDetailDTO itemDetail = itemRepositoryImpl.findItemDetail(1L, 1L);
        System.out.println("itemDetail = " + itemDetail);
    }



}