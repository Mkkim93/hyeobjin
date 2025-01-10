package com.hyeobjin.web.item.api;

import com.hyeobjin.application.dto.item.CreateItemDTO;
import com.hyeobjin.application.dto.item.FindByItemDTO;
import com.hyeobjin.application.service.item.ItemService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.media.SchemaProperty;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@Slf4j
@RestController
@RequiredArgsConstructor
public class ItemApiController {

    private final ItemService itemService;

    @PostMapping(value = "/items", consumes = MediaType.MULTIPART_FORM_DATA_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "새로운 제품 등록", description = "제품의 모든 정보 등록")
    public ResponseEntity<?> save(@ModelAttribute("item") CreateItemDTO createItemDTO,
                                  @RequestPart("files") List<MultipartFile> files) throws IOException {
        itemService.saveItem(createItemDTO, files);
        return ResponseEntity.ok("success");
    }

    @Operation(summary = "제품 조회 (품번)", description = "품번으로 제품 조회")
    @GetMapping("/item")
    public ResponseEntity<FindByItemDTO> findOne(@RequestParam("itemNum") String itemNum) {
        FindByItemDTO findByItemDTO = new FindByItemDTO();
        findByItemDTO.setItemNum(itemNum);
        return ResponseEntity.ok(itemService.findByItemOne(findByItemDTO));
    }

    @Operation(summary = "제품 상세 조회", description = "제품 상세 정보 조회")
    @GetMapping("/itemDetail")
    public ResponseEntity<FindByItemDTO> findOneDetail() {
        // TODO
        return null;
    }
}
