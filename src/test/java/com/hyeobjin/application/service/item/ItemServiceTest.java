package com.hyeobjin.application.service.item;

import com.hyeobjin.application.admin.dto.item.CreateItemDTO;
import com.hyeobjin.application.admin.dto.item.UpdateItemDTO;
import com.hyeobjin.application.admin.service.item.AdminItemService;
import com.hyeobjin.application.common.dto.item.FindByItemDTO;
import com.hyeobjin.application.common.service.item.ItemService;
import com.hyeobjin.domain.entity.item.Item;
import com.hyeobjin.domain.repository.item.ItemRepository;
import jakarta.persistence.EntityManager;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@Slf4j
@SpringBootTest
class ItemServiceTest {

    @Autowired
    ItemService itemService;

    @Autowired
    AdminItemService adminItemService;

    @Autowired
    ItemRepository itemRepository;

    @Autowired
    EntityManager entityManager;

    @Test
    @DisplayName("item save test (제조사 제품 등록)")
    void save() throws IOException {

        List<MultipartFile> files = new ArrayList<>();
        CreateItemDTO createItemDTO = new CreateItemDTO();
        createItemDTO.setItemName("test itemName05");
        createItemDTO.setItemUse("test itemUse05");
        createItemDTO.setItemSpec("test itemSpec05");
        createItemDTO.setItemDescription("test itemDescription05");
        createItemDTO.setItemType("prod05");
        createItemDTO.setItemNum("K101");
        createItemDTO.setManuName("KCC");

        adminItemService.saveItem(createItemDTO, files);
    }

    @Test
    @DisplayName("제품 & 파일 등록")
    void saveItemAndFiles() {
        // TODO
        List<MultipartFile> files = new ArrayList<>();

        CreateItemDTO createItemDTO = new CreateItemDTO();

    }

    @Test
    @DisplayName("제품 품번 조회")
    void findOneItemByItemNum() {

        FindByItemDTO findByItemDTO = new FindByItemDTO();
        findByItemDTO.setItemNum("K101");

        itemService.findByItemOne(findByItemDTO);
//        System.out.println("findOneItem = " + findOneItem);

//        assertThat(findOneItem.getItemNum()).isEqualTo(findByItemDTO.getItemNum());
    }

    @Test
    @DisplayName("제품 수정 (item 객체만 수정)")
    void updateItem() throws IOException {
        UpdateItemDTO updateItemDTO = new UpdateItemDTO();
        updateItemDTO.setItemId(16L);
        updateItemDTO.setItemName("up01 itemName");
//        updateItemDTO.setItemDescription("up01 itemDescription");
//        updateItemDTO.setItemSpec("up01 itemSpec");
        updateItemDTO.setItemUse("up01 itemUse");
//        updateItemDTO.("up01 itemType");
        updateItemDTO.setItemNum("up01 250B");

        Item item = itemRepository.findById(updateItemDTO.getItemId()).get();

        assertThat("up01 itemName").isEqualTo(item.getItemName());
        assertThat("up01 itemDescription").isEqualTo(item.getItemDescription());
        assertThat("up01 itemUse").isEqualTo(item.getItemUse());
    }

    @Test
    @DisplayName("제품의 모든 pk & itemNum 조회")
    void findPKAndItemNumList() {
        Long manuId = 1L;
        itemService.findAllItemNumList(manuId).stream().forEach(System.out::println);
    }
}