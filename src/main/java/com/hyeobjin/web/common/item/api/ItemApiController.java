package com.hyeobjin.web.common.item.api;

import com.fasterxml.jackson.annotation.JsonView;
import com.hyeobjin.application.common.dto.item.FindByItemDTO;
import com.hyeobjin.application.common.service.item.ItemService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@Tag(name = "COMMON_ITEM", description = "Item 등록 관련 API")
@RestController
@RequestMapping("/items")
@RequiredArgsConstructor
public class ItemApiController {

    private final ItemService itemService;

    /**
     * 제품 소개 페이지에서 네비게이션바를 누르면 해당 품번의 제품의 모든 정보를 조회
     * swagger : O
     * @param itemId
     * role : common
     * @return
     */
    @Operation(summary = "제품 조회 (품번)", description = "품번으로 제품을 상세 조회하는 API 입니다.")
    @GetMapping
    public ResponseEntity<FindByItemDTO> findOne(@RequestParam(value = "itemId", required = false) Long itemId) {
        log.info("itemNum log : ", itemId);
        System.out.println("itemId = " + itemId);
        FindByItemDTO findByItemDTO = new FindByItemDTO();
        findByItemDTO.setItemId(itemId);
//        findByItemDTO.setManuId(manuId);
        return ResponseEntity.ok(itemService.findByItemOne(findByItemDTO));
    }

    /**
     *
     * @param manuId
     * @return
     */
    @JsonView(FindByItemDTO.SummaryView.class)
    @GetMapping("/numbers")
    @Operation(summary = "제품의 모든 pk 조회", description = "제품 품번을 모두 조회하는 API 입니다.")
    public ResponseEntity<List<FindByItemDTO>> findAllItemNum(@RequestParam("manuId") Long manuId) {
        return ResponseEntity.ok(itemService.findAllItemNumList(manuId));
    }
}
