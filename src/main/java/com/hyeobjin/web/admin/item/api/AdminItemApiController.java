package com.hyeobjin.web.admin.item.api;

import com.hyeobjin.application.admin.dto.item.FindAdminDetailDTO;
import com.hyeobjin.application.admin.dto.item.FindAdminItemDTO;
import com.hyeobjin.application.admin.service.item.AdminItemService;
import com.hyeobjin.application.common.dto.item.CreateItemDTO;
import com.hyeobjin.application.common.dto.item.FindByItemDTO;
import com.hyeobjin.application.common.dto.item.UpdateItemDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Map;

@Slf4j
@Tag(name = "ADMIN_ITEM", description = "관리자 권한으로 제품의 CRUD 관리를 위한 REST API 입니다.")
@RestController
@RequestMapping("/admin/items")
@RequiredArgsConstructor
public class AdminItemApiController {

    private final AdminItemService adminItemService;

    @Operation(summary = "관리자 제품 조회" , description = "관리자가 모든 제품을 조건별로 조회하는 페이징 API 입니다.")
    @GetMapping
    public ResponseEntity<Page<FindAdminItemDTO>> findItemPageList(
                                              @RequestParam(name = "page", defaultValue = "0") int page,
                                              @RequestParam(name = "size", defaultValue = "10") int size,
                                              @RequestParam(name = "manuName", required = false) String manuName
                                              ) {
        PageRequest pageRequest = PageRequest.of(page, size);

        Page<FindAdminItemDTO> adminItemPages = adminItemService.findAdminItemPages(pageRequest, manuName);

        return ResponseEntity.ok(adminItemPages);
    }

    @Operation(summary = "관리자 제품 상세 조회", description = "관리자가 제품을 상세 조회하기 위한 API 입니다.")
    @GetMapping("/detail")
    public ResponseEntity<FindAdminDetailDTO> findItemDetail(@RequestParam("manuId") Long manuId,
                                                             @RequestParam("itemId") Long itemId) {
        FindByItemDTO findAdminItemDTO = new FindByItemDTO();
        findAdminItemDTO.setItemId(itemId);
        findAdminItemDTO.setManuId(manuId);
        return ResponseEntity.ok(adminItemService.findByItemDetail(findAdminItemDTO));
    }

    @Operation(summary = "관리자 제품 상세 조회 (수정)", description = "관리자가 제품 pk 를 기준으로 상세조회 하기 위한 API 입니다.")
    @GetMapping("/modify")
    public ResponseEntity<FindAdminDetailDTO> findItemModify(@RequestParam("itemId") Long itemId) {

        FindByItemDTO findByItemDTO = new FindByItemDTO();
        findByItemDTO.setItemId(itemId);
        
        return ResponseEntity.ok(adminItemService.findByItemId(itemId));
    }

    /**
     * 관리자가 제품을 삭제하면 해당 제품과 연관된 파일 데이터도 함께 영구적으로 삭제됩니다.
     * @param itemId 관리자가 제품을 영구적으로 삭제하기 위해 클라이언트 체크박스로 제품의 PK 를 리스트 형태로 요청합니다.
     * @return
     */
    @Operation(summary = "관리자 제품 삭제 (영구삭제)", description = "관리자가 제품의 데이터를 모두 영구적으로 삭제하는 API 입니다.")
    @DeleteMapping
    public ResponseEntity<String> deleteItem(@RequestBody List<Long> itemId) {
        try {
            adminItemService.deleteItemIds(itemId);
            return ResponseEntity.ok().build();
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(null);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    /**
     * 제품 등록 시 파일 데이터와 함께 저장
     * postman api : O (swagger ui 에서는 하나의 api 에서 서로 다른 두개의 데이터 타입을 파싱하지 못하기 때문에 postman api 를 통해 테스트 진행.)
     * @param createItemDTO
     * @param itemFiles
     * admin
     * @return
     * @throws IOException
     */
    @Operation(summary = "제품 등록", description = "제품의 모든 정보 등록하는 API 입니다.")
    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<?> save(@ModelAttribute CreateItemDTO createItemDTO,
                                  @RequestPart(value = "files", required = false) List<MultipartFile> itemFiles
                                  ) throws IOException {
        log.info("files ={}", itemFiles);
        adminItemService.saveItem(createItemDTO, itemFiles);
        return ResponseEntity.ok("제품이 성공적으로 등록 되었습니다.");
    }

    /**
     * 제품 수정
     * @param updateItemDTO
     * @param files
     * # postman api : O
     * ROLE : ADMIN
     * @return
     * @throws IOException
     */
    @Operation(summary = "제품 수정", description = "제품의 모든 정보(manufacturer, file, item)를 수정하는 API 입니다.")
    @PostMapping(value = "/update", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<?> findOneDetail(@ModelAttribute UpdateItemDTO updateItemDTO,
                                           @RequestPart(value = "files", required = false) List<MultipartFile> files) throws IOException {
        adminItemService.update(updateItemDTO, files);
        return ResponseEntity.ok("file update success");
    }

    /**
     * 제품 삭제
     * swagger : X
     * ROLE : ADMIN
     * // TODO 안쓸듯
     */
    @Operation(summary = "제품 삭제", description = "관리자가 제품의 정보를 폼에서 삭제하고 제품의 정보는 데이터베이스에 유지 하는 API 입니다.")
    @PostMapping("/change") // TODO 제품의 PK 로 조회하지 않고 DTO 나 itemNum 으로 조회 후 삭제 업데이트 ?
    public ResponseEntity<String> deleteItem(@RequestParam("itemId") Long itemId) {
        adminItemService.delete(itemId);
        return ResponseEntity.ok("item delete success");
    }

}
