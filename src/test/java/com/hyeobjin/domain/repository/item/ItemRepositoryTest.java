package com.hyeobjin.domain.repository.item;

import com.hyeobjin.application.admin.dto.item.CreateItemDTO;
import com.hyeobjin.application.admin.dto.item.FindAdminDetailDTO;
import com.hyeobjin.application.admin.dto.item.FindAdminItemDTO;
import com.hyeobjin.application.common.dto.item.FindByItemDTO;
import com.hyeobjin.application.common.dto.item.FindItemNameDTO;
import com.hyeobjin.domain.entity.item.Item;
import lombok.extern.slf4j.Slf4j;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.assertj.core.api.Assertions.*;
import static org.assertj.core.api.Assertions.assertThat;

@Slf4j
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Transactional
@DisplayName("제품 조회/등록/수정 테스트")
@DataJpaTest
class ItemRepositoryTest {

    @Autowired
    private ItemRepository itemRepository;

    @Autowired
    private ItemRepositoryImpl itemRepositoryImpl;

    @Test
    @DisplayName("등록 : 제품 등록")
    void save() {
        CreateItemDTO createItemDTO = new CreateItemDTO();
        createItemDTO.setItemName("test ItemName02");
        createItemDTO.setItemSpec("test ItemSpec02");
        createItemDTO.setItemUse("test ItemUse02");
        createItemDTO.setItemTypeId(1L);
        createItemDTO.setItemYN(false);
        createItemDTO.setItemDescription("test ItemDescription test");
    }

    @Test
    @DisplayName("조회 : 제품 상세 조회")
    void findDetailItem() {

        // given
        FindByItemDTO findByItemDTO = new FindByItemDTO();
        findByItemDTO.setItemId(1L);
        findByItemDTO.setManuId(1L);

        // when
        FindByItemDTO byItem = itemRepositoryImpl.findByItem(findByItemDTO.getManuId(),
                findByItemDTO.getItemId());

        // then
        System.out.println("findOneByItem = " + byItem);
        assertThat(byItem.getItemId().equals(findByItemDTO.getItemId()));
    }

    @Test
    @DisplayName("조회 : 특정 제조사_모든 품번 조회")
    void findAllItemId() {

        // given
        Long manuId = 1L;

        // when
        List<FindByItemDTO> result = itemRepository.findAllItemId(manuId);

        // then
        assertThat(result).allMatch(s -> s.getManuId().equals(manuId));
    }

    @Test
    @DisplayName("조회 : 관리자 제품_제조사별 목록조회_페이징")
    void findItemAllPage() {

        // given
        PageRequest pageRequest = PageRequest.of(0, 5);

        // when
        Page<FindAdminItemDTO> kcc = itemRepositoryImpl.findItemList(pageRequest, "KCC");

        List<FindAdminItemDTO> content = kcc.getContent();

        // then
        assertThat(content).allMatch(c -> c.getManuId().equals(1L));
    }

    @Test
    @DisplayName("조회 : 관리자 페이지 상세 조회")
    void findByItemDetail() {

        // given
        Long itemId = 1L;

        // when
        FindAdminDetailDTO itemDetail = itemRepositoryImpl.findItemDetail(itemId);

        // then
        assertThat(itemDetail.getItemId()).isEqualTo(itemId);
        System.out.println("itemDetail = " + itemDetail);
    }

    @Test
    @DisplayName("제조사와 제품 타입에 해당하는 제품의 품번 조회")
    void findItemNum() {

        // given
        Long manuId = 1L;
        Long typeId = 1L;

        // when
        List<FindItemNameDTO> itemNum = itemRepository.findItemNum(manuId, typeId);

        // then
        assertThat(itemNum).isNotNull();

    }

}