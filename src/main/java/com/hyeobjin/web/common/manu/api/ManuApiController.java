package com.hyeobjin.web.common.manu.api;

import com.hyeobjin.application.common.dto.manu.ManufactureDTO;
import com.hyeobjin.application.common.service.manufacturer.ManufacturerService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@Tag(name = "Manufacturer", description = "제조사 관련 API")
@RestController
@RequestMapping("/manufacturers")
@RequiredArgsConstructor
public class ManuApiController {

    private final ManufacturerService manufacturerService;
    /**
     * # 모든 제조사 조회
     * # swagger : O
     * - 메인 폼에서 제품 소개를 클릭하면 해당 드롭다운 메뉴에 조회될 제조사 데이터를 조회한다
     * - common
     * @return
     */
    @Operation(summary = "모든 제조업체 조회", description = "모든 제조업체를 조회하는 API 입니다.")
    @GetMapping
    public ResponseEntity<List<ManufactureDTO>> findAll() {
        List<ManufactureDTO> manufacturers = manufacturerService.findAll();
        return ResponseEntity.ok(manufacturers);
    }
}
