package com.hyeobjin.web.admin.board.api;

import com.hyeobjin.application.admin.service.board.BoardImageService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@Slf4j
@RestController
@RequestMapping("/image")
@Tag(name = "COMMON_IMAGE", description = "에디터로 업로드한 이미지를 AWS S3 버킷으로 전송하기 위한 API 입니다.")
@RequiredArgsConstructor
public class ImageController {

    private final BoardImageService imageService;

    @Operation(summary = "이미지 업로드", description = "이미지를 업로드 하면 S3 버킷으로 전송되고 정적 파일을 삭제하기 위한 API 입니다.")
    @PostMapping("/upload")
    @ResponseBody
    public Map<String, Object> imageUpload(@RequestParam("image") MultipartFile file) {
        log.info("imageUpload controller");

        Map<String, Object> responseData = new HashMap<>();

        try {
            String s3url = imageService.imageUpload(file);

            responseData.put("url", s3url);

            log.info("location url ={}", s3url);

        } catch (IOException e) {
            responseData.put("error", "이미지 업로드 실패");
        }

        log.info("responseData ={}", responseData);
        return responseData;
    }
}