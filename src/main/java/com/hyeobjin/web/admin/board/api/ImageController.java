package com.hyeobjin.web.admin.board.api;

import com.hyeobjin.application.admin.service.board.BoardImageService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartRequest;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@Slf4j
@Controller
@RequiredArgsConstructor
public class ImageController {

    private final BoardImageService imageService;

    @PostMapping("/image/upload")
    @ResponseBody
    public Map<String, Object> imageUpload(@RequestParam("image") MultipartFile file) {
        log.info("imageUpload controller");
        Map<String, Object> responseData = new HashMap<>();

        try {
            String s3url = imageService.imageUpload(file);

            // ✅ TinyMCE에서 인식할 수 있도록 "location" 필드 사용
            responseData.put("url", s3url);
            log.info("location url ={}", s3url);
        } catch (IOException e) {
            responseData.put("error", "이미지 업로드 실패");
        }

        log.info("responseData ={}", responseData);
        return responseData;
    }

}
