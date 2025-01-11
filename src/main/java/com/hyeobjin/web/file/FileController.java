package com.hyeobjin.web.file;

import com.hyeobjin.application.service.file.FileBoxService;
import com.hyeobjin.domain.entity.file.FileBox;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.util.UriUtils;

import java.net.MalformedURLException;
import java.nio.charset.StandardCharsets;

@Slf4j
@Controller
@RequestMapping("/files")
@RequiredArgsConstructor
public class FileController {

    private final FileBoxService fileBoxService;

    @PostMapping("/download/{fileBoxId}")
    public ResponseEntity<Resource> download(@PathVariable("fileBoxId") Long fileBoxId) throws MalformedURLException {
        FileBox fileBox = fileBoxService.findById(fileBoxId);

        if (fileBox == null) {
            return ResponseEntity.notFound().build(); // 파일이 없을 경우 404 에러
        }

        String uploadFileName = fileBox.getFileOrgName();
        String fileBoxName = fileBox.getFileName();

        UrlResource urlResource = new UrlResource("file:" + fileBoxService.getFullPath(fileBoxName));

        log.info("urlResource={}", urlResource);
        log.info("fileName={}", uploadFileName);

        String encodeFileName = UriUtils.encode(uploadFileName, StandardCharsets.UTF_8);
        String contentDisposition = "attachment; filename=\"" + encodeFileName + "\"";

        // 파일 응답
        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, contentDisposition)
                .body(urlResource);
    }
}
