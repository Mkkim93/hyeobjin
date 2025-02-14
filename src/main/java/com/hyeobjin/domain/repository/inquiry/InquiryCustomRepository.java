package com.hyeobjin.domain.repository.inquiry;

import com.hyeobjin.application.admin.dto.inquiry.FindAdminInquiryDetailDTO;
import org.springframework.stereotype.Repository;

public interface InquiryCustomRepository {

    FindAdminInquiryDetailDTO findDetail(Long inquiryId);
}
