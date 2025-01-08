package com.hyeobjin.application.service.item;

import com.hyeobjin.application.dto.item.CreateItemDTO;
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

import static org.junit.jupiter.api.Assertions.*;

@Slf4j
@SpringBootTest
class ItemServiceTest {

    @Autowired ItemService itemService;

    @Test
    @DisplayName("item save test (현재 존재하는 제조사의 제품을 등록)")
    void save() throws IOException {

        List<MultipartFile> files = new ArrayList<>();
        CreateItemDTO createItemDTO = new CreateItemDTO();
        createItemDTO.setItemName("test itemName04");
        createItemDTO.setItemUse("test itemUse04");
        createItemDTO.setItemSpec("test itemSpec04");
        createItemDTO.setItemDescription("test itemDescription03");
        createItemDTO.setItemType("door");
        createItemDTO.setItemNum("K100");
        createItemDTO.setMenuName("KCC");

        itemService.saveItem(createItemDTO, files);

    }
}