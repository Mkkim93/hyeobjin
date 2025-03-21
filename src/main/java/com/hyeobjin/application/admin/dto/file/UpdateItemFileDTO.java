package com.hyeobjin.application.admin.dto.file;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 제품 : item
 * 제품 저장 시 file 을 저장할 DTO 클래스
 * 제품 등록 시 file 데이터를 먼저 저장하고 저장된 파일의 외래키를
 * 제품의 서비스 레이어로 전달한다.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class UpdateItemFileDTO {

    private Long fileBoxId;
    private String fileName;
    private String fileOrgName;
    private Long fileSize;
    private String fileType;
    private String filePath;
    private Boolean isMain;
    private Long itemId;
}
