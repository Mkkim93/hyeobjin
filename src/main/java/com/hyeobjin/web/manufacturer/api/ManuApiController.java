package com.hyeobjin.web.manufacturer.api;

import com.hyeobjin.application.dto.manu.ManufactureDTO;
import com.hyeobjin.application.service.manufacturer.ManufacturerService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@Tag(name = "Manufacturer", description = "제조사 관련 API")
@RestController
@RequestMapping("/manufacturer")
@RequiredArgsConstructor
public class ManuApiController {

    private final ManufacturerService manufacturerService;

    /**
     * # 새로운 제조사 등록
     * # swagger : O
     *
     * @param
     * @ManuName : 등록할 제조사명
     */
    @Operation(summary = "새로운 제조업체 등록", description = "관리자가 제조업체를 등록하는 API 입니다.")
    @PostMapping
    public ResponseEntity<ManufactureDTO> save(@RequestBody @Valid ManufactureDTO manufactureDTO) {
        manufacturerService.saveManu(manufactureDTO);
        return ResponseEntity.ok(manufactureDTO);
    }

    /**
     * # 모든 제조사 조회
     * # swagger : O
     * - 메인 폼에서 제품 소개를 클릭하면 해당 드롭다운 메뉴에 조회될 제조사 데이터를 조회한다
     *
     * @return
     */
    @Operation(summary = "모든 제조업체 조회", description = "모든 제조업체를 등록하는 API 입니다.")
    @GetMapping
    public ResponseEntity<List<ManufactureDTO>> findAll() {
        List<ManufactureDTO> manufacturers = manufacturerService.findAll();
        return ResponseEntity.ok(manufacturers);
    }

    /**
     * # 제조사 정보 수정
     * # swagger : O
     * - 관리자가 제조사 정보를 수정한다.
     */
    @Operation(summary = "제조업체 수정", description = "관리자가 제조업체명을 수정하는 API 입니다.")
    @PostMapping("/update")
    public ResponseEntity<String> update(@RequestBody ManufactureDTO manufactureDTO) {
        try {
            Integer updateCount = manufacturerService.update(manufactureDTO);
            if (updateCount != 1) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("제조업체를 찾을 수 없거나 변경 사항이 없습니다.");
            }
            return ResponseEntity.ok("제조업체를 변경 되었습니다.");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("제조업체를 업데이트하는 동안 오류가 발생했습니다.");
        }
    }

    /**
     * # 제조사 정보 삭제
     * # swagger : O
     * - 관리자가 제조사 정보를 삭제한다.
     */
    @Operation(summary = "제조업체 삭제", description = "관리자가 제조업체를 삭제하는 API 입니다.")
    @PostMapping("/delete")
    public ResponseEntity<String> delete(@RequestBody ManufactureDTO manufactureDTO) {
        try {
            Integer deleteCount = manufacturerService.delete(manufactureDTO);
            if (deleteCount != 1) {
                return ResponseEntity.ok("제조업체 정보를 찾을 수 없거나 삭제에 실패 하였습니다.");
            }
            return ResponseEntity.ok("제조업체 정보가 삭제 되었습니다.");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("제조업체를 삭제하는 동안 오류가 발생했습니다.");
        }
    }

    /**
     * # 제조사 정보 복구
     * # swagger : O
     * - 관리자가 삭제한 제조사 정보를 다시 복구한다.
     */
    @Operation(summary = "제조업체 복구", description = "관리자가 삭제한 제조업체를 복구하는 API 입니다.")
    @PostMapping("/restore")
    public ResponseEntity<String> restore(@RequestBody ManufactureDTO manufactureDTO) {
        try {
            Integer restore = manufacturerService.restore(manufactureDTO);
            if (restore != 1) {
                return ResponseEntity.ok("제조업체 정보 복구를 실패하였습니다.");
            }
            return ResponseEntity.ok("제조업체 정보가 성공적으로 복구 되었습니다.");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("제조업체를 복구하는 동안 오류가 발생하였습니다.");
        }
    }
}
