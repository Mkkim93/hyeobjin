package com.hyeobjin.web.admin.item.glass;

import com.hyeobjin.application.admin.dto.item.glass.GlassSpecDTO;
import com.hyeobjin.application.admin.service.item.GlassSpecService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@Tag(name = "ADMIN_GLASS", description = "관리자가 제품의 유리 사양을 관리하기 위한 API 입니다.")
@RequestMapping("/admin/glass")
@RequiredArgsConstructor
public class AdminGlassSpecController {

    private final GlassSpecService glassSpecService;

    @GetMapping
    @Operation(summary = "관리자 유리 스펙 조회 API", description = "관리자가 현재 유리 스펙 전체 리스트를 조회하기 위한 API 입니다.")
    public ResponseEntity<List<GlassSpecDTO>> findAll() {
        return ResponseEntity.ok(glassSpecService.findGlassList());
    }

    @PostMapping
    @Operation(summary = "관리자 유리 스펙 등록 API", description = "관리자가 새로운 유리 스펙을 등록하기 위한 API 입니다.")
    public ResponseEntity<?> save(@RequestParam("newGlassSpec") String newGlassSpec) {
        glassSpecService.save(newGlassSpec);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @PostMapping("/update/{glassSpecId}")
    @Operation(summary = "관리자 유리 스펙 수정 API", description = "관리자가 기존의 유리 스펙을 수정하기 위한 API 입니다.")
    public ResponseEntity<?> update(@PathVariable("glassSpecId") Long glassSpecId,
                                    @RequestBody String glassSpecSize) {
        glassSpecService.update(glassSpecId, glassSpecSize);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping
    @Operation(summary = "관리자 유리 스펙 삭제 API", description = "관리자가 기존의 유리 스펙 정보를 삭제하기 위한 API 입니다.")
    public ResponseEntity<?> delete(@RequestParam("glassSpecId") Long glassSpecId) {
        glassSpecService.delete(glassSpecId);
        return ResponseEntity.noContent().build();
    }
}
