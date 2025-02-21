package com.hyeobjin.application.admin.dto.inquiry;

import com.hyeobjin.application.admin.dto.file.AdminInquiryFileDTO;
import com.querydsl.core.annotations.QueryProjection;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Data
@NoArgsConstructor
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
    private String manuName;
    private String itemTypeName;
    private String itemName;

    private List<AdminInquiryFileDTO> inquiryFiles;

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
}
