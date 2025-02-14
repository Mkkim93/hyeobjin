package com.hyeobjin.application.admin.dto.inquiry;

import com.hyeobjin.application.admin.dto.file.AdminInquiryFileDTO;
import com.querydsl.core.annotations.QueryProjection;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class FindAdminInquiryDetailDTO {

    private Long inquiryId;

    private String title;
    private String content;
    private String writer;
    private String tel;
    private String email;
    private String addr;
    private String detailAddr;
    private LocalDateTime createAt;

    private Long manuId;
    private String manuName;

    private Long itemTypeId;
    private String itemTypeName;

    private Long itemId;
    private String itemName;

    private List<AdminInquiryFileDTO> inquiryFiles;
    private Long fileBoxId;
    private String filePath;
    private String fileOrgName;

    @QueryProjection
    public FindAdminInquiryDetailDTO(Long inquiryId, String title, String content,
                                     String writer, String tel, String email,
                                     String addr, String detailAddr, LocalDateTime createAt,
                                     String manuName, String itemTypeName, String itemName,
                                    List<AdminInquiryFileDTO> inquiryFiles) {
        this.inquiryId = inquiryId;
        this.title = title;
        this.content = content;
        this.writer = writer;
        this.tel = tel;
        this.email = email;
        this.addr = addr;
        this.detailAddr = detailAddr;
        this.createAt = createAt;
        this.manuName = manuName;
        this.itemTypeName = itemTypeName;
        this.itemName = itemName;
        this.inquiryFiles = inquiryFiles;
    }

    @Override
    public String toString() {
        return "FindAdminInquiryDetailDTO{" +
                "InquiryId=" + inquiryId +
                ", title='" + title + '\'' +
                ", content='" + content + '\'' +
                ", writer='" + writer + '\'' +
                ", tel='" + tel + '\'' +
                ", email='" + email + '\'' +
                ", addr='" + addr + '\'' +
                ", detailAddr='" + detailAddr + '\'' +
                ", createAt=" + createAt +
                ", manuName='" + manuName + '\'' +
                ", itemTypeName='" + itemTypeName + '\'' +
                ", itemName='" + itemName + '\'' +
                ", inquiryFiles=" + inquiryFiles +
                '}';
    }
}
