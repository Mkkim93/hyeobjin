package com.hyeobjin.application.admin.service.board;

import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.hyeobjin.config.s3.S3Config;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartRequest;

import java.io.File;
import java.io.IOException;
import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class BoardImageService {

    private S3Config s3Config;

    @Value("${cloud.aws.s3.bucket}")
    private String bucket;

    @Value("${file.board.dir}")
    private String localLocation;

    @Autowired
    public BoardImageService(S3Config s3Config) {
        this.s3Config = s3Config;
    }

    public String imageUpload(MultipartRequest request) throws IOException {

        log.info("imageUpload service");
        MultipartFile file = request.getFile("upload");

        String fileName = file.getOriginalFilename();
        String ext = fileName.substring(fileName.indexOf("."));

        String uuidFileName = UUID.randomUUID() + ext;
        String localPath = localLocation + uuidFileName;

        File localFile = new File(localPath);
        file.transferTo(localFile);


        s3Config.amazonS3Client().putObject(new PutObjectRequest(bucket, uuidFileName, localFile).withCannedAcl(CannedAccessControlList.PublicRead));
        String s3Url = s3Config.amazonS3Client().getUrl(bucket, uuidFileName).toString();

        localFile.delete();

        return s3Url;
    }
}
