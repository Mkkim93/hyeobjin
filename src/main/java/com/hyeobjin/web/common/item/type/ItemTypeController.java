package com.hyeobjin.web.common.item.type;

import com.hyeobjin.application.admin.service.item.AdminItemTypeService;
import com.hyeobjin.application.common.dto.item.type.FindItemTypeDTO;
import com.hyeobjin.application.common.dto.item.type.ItemTypeDTO;
import com.hyeobjin.application.common.service.item.type.ItemTypeService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@Tag(name = "COMMON_ITEM_TYPE", description = "사용자가 제품 타입을 기준으로 조회하기 위한 API 입니다.")
@RequestMapping("/type")
@RequiredArgsConstructor
public class ItemTypeController {

    private final ItemTypeService itemTypeService;

    @GetMapping("/category")
    @Operation(summary = "사용자 제품 타입 별 제품명 조회", description = "사용자가 제품의 카테고리(제품 타입) 기준으로 제품명 조회를 위한 API 입니다.")
    public ResponseEntity<List<FindItemTypeDTO>> findAllSelect(@RequestParam("itemTypeId") Long itemTypeId,
                                                               @RequestParam("manuId") Long manuId) {
        return ResponseEntity.ok(itemTypeService.itemTypeSelectList(itemTypeId, manuId));
    }

    @GetMapping
    @Operation(summary = "사용자 제품 타입 조회", description = "사용자가 전체 제품의 타입을 조회하기 위한 API 입니다.")
    public ResponseEntity<List<ItemTypeDTO>> findAll(@RequestParam("manuId") Long manuId) {
        return ResponseEntity.ok(itemTypeService.findAll(manuId));
    }
}
