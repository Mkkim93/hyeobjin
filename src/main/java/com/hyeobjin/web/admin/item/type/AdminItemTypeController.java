package com.hyeobjin.web.admin.item.type;

import com.hyeobjin.application.admin.dto.item.type.AdminItemTypeDTO;
import com.hyeobjin.application.admin.dto.item.type.UpdateItemTypeDTO;
import com.hyeobjin.application.admin.service.item.AdminItemTypeService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@Tag(name = "ADMIN_ITEM_TYPE", description = "관리자가 제품 타입을 관리하기 위한 API 입니다.")
@RestController
@RequestMapping("/admin/type")
@RequiredArgsConstructor
public class AdminItemTypeController {

    private final AdminItemTypeService adminItemTypeService;

    @GetMapping
    @Operation(summary = "관리자가 제품 타입을 조회 API", description = "관리자가 기존의 모든 제품 타입 데이터를 조회하기 위한 API 입니다.")
    public ResponseEntity<List<AdminItemTypeDTO>> findAll() {
        return ResponseEntity.ok(adminItemTypeService.findItemTypeList());
    }

    @PostMapping
    @Operation(summary = "관리자 제품 타입 등록 API", description = "관리자가 새로운 제품의 타입을 등록하기 위한 API 입니다.")
    public ResponseEntity<Void> save(@RequestParam("itemTypeName") String itemTypeName) {
        adminItemTypeService.save(itemTypeName);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @PostMapping("/update/{itemTypeId}")
    @Operation(summary = "관리자 제품 타입 수정 API", description = "관리자가 기존 제품의 타입을 수정하기 위한 API 입니다.")
    public ResponseEntity<Void> update(@PathVariable("itemTypeId") Long itemTypeId, @RequestBody UpdateItemTypeDTO updateItemTypeDTO) {
        adminItemTypeService.updateItemType(itemTypeId, updateItemTypeDTO);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping
    @Operation(summary = "관리자 제품 타입 삭제 API", description = "관리자가 기존 제품의 타입을 삭제하기 위한 API 입니다.")
    public ResponseEntity<Void> delete(@RequestParam("itemTypeId") Long itemTypeId) {
        adminItemTypeService.deleteItemType(itemTypeId);
        return ResponseEntity.noContent().build();
    }
}
