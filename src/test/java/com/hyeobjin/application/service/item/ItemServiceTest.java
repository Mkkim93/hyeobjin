package com.hyeobjin.application.service.item;

import com.hyeobjin.application.dto.item.CreateItemDTO;
import com.hyeobjin.application.dto.item.FindByItemDTO;
import com.hyeobjin.domain.repository.ItemRepository;
import lombok.extern.slf4j.Slf4j;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@Slf4j
@SpringBootTest
class ItemServiceTest {

    @Autowired
    ItemService itemService;

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
        createItemDTO.setMenuName("KCC");

        itemService.saveItem(createItemDTO, files);
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

        FindByItemDTO findOneItem = itemService.findByItemOne(findByItemDTO);
        System.out.println("findOneItem = " + findOneItem);

        assertThat(findOneItem.getItemNum()).isEqualTo(findByItemDTO.getItemNum());
    }
}