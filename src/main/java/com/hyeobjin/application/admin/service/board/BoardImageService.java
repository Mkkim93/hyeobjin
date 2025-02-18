package com.hyeobjin.application.admin.service.board;

import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.hyeobjin.config.s3.S3Config;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class BoardImageService {

    private final S3Config s3Config;

    @Value("${cloud.aws.s3.bucket}")
    private String bucket;

    @Value("${file.board.dir}")
    private String localLocation;

    public String imageUpload(MultipartFile file) throws IOException {
        log.info("imageUpload service 호출됨");

        // 파일이 비어 있는 경우 예외 처리
        if (file == null || file.isEmpty()) {
            throw new IllegalArgumentException("업로드할 파일이 없습니다.");
        }

        String fileName = file.getOriginalFilename();

        // 파일 이름이 없는 경우 예외 처리
        if (fileName == null || !fileName.contains(".")) {
            throw new IllegalArgumentException("올바른 파일 형식이 아닙니다.");
        }

        String ext = fileName.substring(fileName.lastIndexOf("."));
        String uuidFileName = UUID.randomUUID() + ext;
        String localPath = localLocation + uuidFileName;

        File localFile = new File(localPath);
        file.transferTo(localFile);

        log.info("로컬 파일 저장 완료: {}", localPath);

        // S3에 업로드
        s3Config.amazonS3Client().putObject(
                new PutObjectRequest(bucket, uuidFileName, localFile)
                        .withCannedAcl(CannedAccessControlList.PublicRead)
        );

        String s3Url = s3Config.amazonS3Client().getUrl(bucket, uuidFileName).toString();
        log.info("S3 업로드 완료: {}", s3Url);

        // 로컬 파일 삭제
        if (localFile.delete()) {
            log.info("로컬 파일 삭제 완료: {}", localPath);
        } else {
            log.warn("로컬 파일 삭제 실패: {}", localPath);
        }

        return s3Url;
    }
}
