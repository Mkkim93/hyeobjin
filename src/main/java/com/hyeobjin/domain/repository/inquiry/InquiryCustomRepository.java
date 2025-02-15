package com.hyeobjin.domain.repository.inquiry;

import com.hyeobjin.application.admin.dto.inquiry.FindAdminInquiryDetailDTO;

public interface InquiryCustomRepository {

    FindAdminInquiryDetailDTO findDetail(Long inquiryId);
}
