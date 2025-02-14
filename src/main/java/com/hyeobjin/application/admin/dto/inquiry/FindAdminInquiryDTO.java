package com.hyeobjin.application.admin.dto.inquiry;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class FindAdminInquiryDTO {

    private Long InquiryId;
    private String title;
    private String writer;
    private LocalDateTime createAt;
}
