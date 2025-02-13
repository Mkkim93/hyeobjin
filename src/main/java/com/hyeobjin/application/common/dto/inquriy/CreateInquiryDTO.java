package com.hyeobjin.application.common.dto.inquriy;

import com.hyeobjin.application.common.dto.file.InquiryFileBoxDTO;
import com.hyeobjin.domain.entity.inquiry.Inquiry;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CreateInquiryDTO {

    private Long InquiryId;
    private String title;
    private String content;
    private String writer;
    private String tel;
    private String email;
    private String addr;
    private String detailAddr;

    private Long itemTypeId;
    private Long manuId;
    private Long itemId;

    public Inquiry toEntity(CreateInquiryDTO createInquiryDTO) {


        return Inquiry.builder()
                .id(createInquiryDTO.getInquiryId())
                .title(createInquiryDTO.getTitle())
                .content(createInquiryDTO.getContent())
                .writer(createInquiryDTO.getWriter())
                .tel(createInquiryDTO.getTel())
                .email(createInquiryDTO.getEmail())
                .addr(createInquiryDTO.getAddr())
                .detailAddr(createInquiryDTO.getDetailAddr())
                .typeId(createInquiryDTO.getItemTypeId())
                .manuId(createInquiryDTO.getManuId())
                .itemId(createInquiryDTO.getItemId())
                .build();
    }
}
