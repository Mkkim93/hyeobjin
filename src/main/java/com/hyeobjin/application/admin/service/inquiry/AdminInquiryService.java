package com.hyeobjin.application.admin.service.inquiry;

import com.hyeobjin.application.admin.dto.inquiry.FindAdminInquiryDTO;
import com.hyeobjin.application.admin.dto.inquiry.FindAdminInquiryDetailDTO;
import com.hyeobjin.domain.entity.inquiry.Inquiry;
import com.hyeobjin.domain.repository.inquiry.InquiryRepository;
import com.hyeobjin.domain.repository.inquiry.InquiryRepositoryImpl;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class AdminInquiryService {

    private final InquiryRepository inquiryRepository;
    private final InquiryRepositoryImpl inquiryRepositoryImpl;

    public Page<FindAdminInquiryDTO> findAll(Pageable pageable) {

        Page<Inquiry> inquiryPage = inquiryRepository.findAll(pageable);

        return inquiryPage.map(inquiry -> new FindAdminInquiryDTO(
                inquiry.getId(),
                inquiry.getTitle(),
                inquiry.getWriter(),
                inquiry.getCreateAt()
        ));
    }

    public FindAdminInquiryDetailDTO findDetailWithFiles(Long inquiryId) {
        return inquiryRepositoryImpl.findDetail(inquiryId);
    }

}
