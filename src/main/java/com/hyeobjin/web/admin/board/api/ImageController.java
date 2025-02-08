package com.hyeobjin.web.admin.board.api;

import com.hyeobjin.application.admin.service.board.BoardImageService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;
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
    public Map<String, Object> imageUpload(MultipartRequest request) {

        log.info("imageUpload controller");
        Map<String, Object> responseData = new HashMap<>();

        try {
            String s3url = imageService.imageUpload(request);

            responseData.put("uploaded", true);
            responseData.put("url", s3url);

        } catch (IOException e) {

            responseData.put("uploaded", false);
        }

        return responseData;
    }
}
