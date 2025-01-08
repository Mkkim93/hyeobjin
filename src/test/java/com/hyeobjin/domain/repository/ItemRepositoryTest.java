package com.hyeobjin.domain.repository;

import com.hyeobjin.application.dto.item.CreateItemDTO;
import com.hyeobjin.domain.entity.Item;
import lombok.extern.slf4j.Slf4j;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

@Slf4j
@SpringBootTest
class ItemRepositoryTest {

    @Autowired ItemRepository itemRepository;

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
        createItemDTO.setItemYN(true);
        createItemDTO.setItemDescription("test ItemDescription test");

        Item savedBeforeItem = createItemDTO.toEntity(createItemDTO);
        System.out.println("savedBeforeItem.getItemDescription() = " + savedBeforeItem.getItemDescription());

        Item savedAfterItem = itemRepository.save(savedBeforeItem);
        System.out.println("savedAfterItem.getItemDescription() = " + savedAfterItem.getItemDescription());

        assertThat(savedBeforeItem).isEqualTo(savedAfterItem);
    }

}