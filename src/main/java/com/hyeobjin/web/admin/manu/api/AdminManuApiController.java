package com.hyeobjin.web.admin.manu.api;

import com.hyeobjin.application.admin.dto.manu.FindManufacturerDTO;
import com.hyeobjin.application.admin.service.manu.AdminManuService;
import com.hyeobjin.application.common.dto.manu.ManufactureDTO;
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
@Tag(name = "ADMIN_MANUFACTURER", description = "관리자 권한으로 제품이 등록될 제조사의 CRUD 를 위한 REST API 입니다.")
@RestController
@RequestMapping("/admin/manu")
@RequiredArgsConstructor
public class AdminManuApiController {

    private final AdminManuService adminManuService;

    /**
     * # 모든 제조사 조회 (해당 제조사에 등록된 제품 수 포함)
     * # swagger : x
     */
    @Operation(summary = "모든 제조사 조회", description = "관리자 메인 폼에서 모든 제조사와 해당 제조사 제품 수 조회 API 입니다.")
    @GetMapping("/count")
    public ResponseEntity<List<FindManufacturerDTO>> findManuItemCount() {
        return ResponseEntity.ok(adminManuService.findManuCountAll());
    }

    /**
     *
     * # 새로운 제조사 등록
     * # swagger : O
     *
     * @param
     * @ManuName : 등록할 제조사명
     */
    @Operation(summary = "새로운 제조업체 등록", description = "관리자가 제조업체를 등록하는 API 입니다.")
    @PostMapping
    public ResponseEntity<ManufactureDTO> save(@RequestBody @Valid ManufactureDTO manufactureDTO) {
        adminManuService.saveManu(manufactureDTO);
        return ResponseEntity.ok(manufactureDTO);
    }

    /**
     *
     * # 제조사 정보 수정
     * # swagger : O
     * - 관리자가 제조사 정보를 수정한다.
     * - 수정 내용
     * 1) 제조사명
     * 2) 등록 or 미등록
     */
    @Operation(summary = "제조업체 수정", description = "관리자가 제조업체명을 수정하는 API 입니다.")
    @PostMapping("/update")
    public ResponseEntity<String> update(@RequestBody ManufactureDTO manufactureDTO) {
        try {
            adminManuService.update(manufactureDTO);
            // 업데이트 성공 시 응답
            return ResponseEntity.ok("제조업체가 변경되었습니다.");
        } catch (Exception e) {
            // 오류 발생 시 응답
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("제조업체를 업데이트하는 동안 오류가 발생했습니다.");
        }
    }

    /**
     *
     * # 제조사 영구 삭제
     * @param manuId 삭제할 제조사의 PK
     * # swagger : O
     * - 관리자가 선택한 삭제할 제조사 PK 를 요청받으면 해당 PK 에 해당하는 제품과 파일데이터가 영구적으로 삭제된다.
     * - 제품과 파일데이터가 정상적으로 삭제되면 해당 제조사가 영구적으로 삭제된다.
     * @return 서버 응답 반환
     */
    @Operation(summary = "제조업체 완전 삭제", description = "제조업체를 DB 에서 영구적으로 삭제하는 API 입니다.")
    @DeleteMapping
    public ResponseEntity<String> delete(@RequestParam("manuId") Long manuId) {
        // 제조업체 삭제
        adminManuService.delete(manuId);
        return ResponseEntity.ok("삭제 성공");
    }

    @GetMapping("/list")
    @Operation(summary = "제품 목록에서 제조사를 필터링", description = "제품 목록에서 제조사별로 필터링 하기 위한 API 입니다.")
    public ResponseEntity<List<FindManufacturerDTO>> findSelect() {
        return ResponseEntity.ok(adminManuService.findAll());

    }
}
