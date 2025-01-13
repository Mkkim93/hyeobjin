package com.hyeobjin.web.item.api;

import com.hyeobjin.application.dto.item.CreateItemDTO;
import com.hyeobjin.application.dto.item.FindByItemDTO;
import com.hyeobjin.application.dto.item.UpdateItemDTO;
import com.hyeobjin.application.service.item.ItemService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@Slf4j
@Tag(name = "Item", description = "Item 등록 관련 API")
@RestController
@RequestMapping("/items")
@RequiredArgsConstructor
public class ItemApiController {

    private final ItemService itemService;

    /**
     * 제품 등록 시 파일 데이터와 함께 저장
     * postman api : O (swagger ui 에서는 하나의 api 에서 서로 다른 두개의 데이터 타입을 파싱하지 못하기 때문에 postman api 를 통해 테스트 진행.)
     * @param createItemDTO
     * @param files
     * @return
     * @throws IOException
     */
    @Operation(summary = "제품 등록", description = "제품의 모든 정보 등록하는 API 입니다.")
    @PostMapping(
            consumes = MediaType.MULTIPART_FORM_DATA_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> save(@ModelAttribute CreateItemDTO createItemDTO,
                                  @RequestPart(value = "files", required = false) List<MultipartFile> files) throws IOException {
        itemService.saveItem(createItemDTO, files);
        return ResponseEntity.ok("제품이 성공적으로 등록 되었습니다.");
    }

    /**
     * 제품 소개 페이지에서 네비게이션바를 누르면 해당 품번의 제품의 모든 정보를 조회
     * swagger : O
     * @param itemNum
     * @return
     */
    @Operation(summary = "제품 조회 (품번)", description = "품번으로 제품 조회하는 API 입니다.")
    @GetMapping
    public ResponseEntity<FindByItemDTO> findOne(@RequestParam("itemNum") String itemNum) {
        FindByItemDTO findByItemDTO = new FindByItemDTO();
        findByItemDTO.setItemNum(itemNum);
        return ResponseEntity.ok(itemService.findByItemOne(findByItemDTO));
    }

    /**
     * 제품 수정
     * @param updateItemDTO
     * @param files
     * # postman api : O
     * @return
     * @throws IOException
     */
    @Operation(summary = "제품 수정", description = "제품의 모든 정보(manufacturer, file, item)를 수정하는 API 입니다.")
    @PostMapping(value = "/update",
            consumes = MediaType.MULTIPART_FORM_DATA_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> findOneDetail(@ModelAttribute UpdateItemDTO updateItemDTO,
                                                @RequestPart("files") List<MultipartFile> files) throws IOException {
        itemService.update(updateItemDTO, files);
        return ResponseEntity.ok("file update success");
    }

    /**
     * 제품 삭제
     * swagger : X
     */
    @Operation(summary = "제품 삭제", description = "관리자가 제품의 정보를 폼에서 삭제하고 제품의 정보는 데이터베이스에 유지 하는 API 입니다.")
    @PostMapping("/delete")
    public ResponseEntity<String> deleteItem(@ModelAttribute UpdateItemDTO updateItemDTO) {
        itemService.delete(updateItemDTO);
        return ResponseEntity.ok("item delete success");
    }
}
