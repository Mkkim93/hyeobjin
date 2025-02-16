package com.hyeobjin.application.admin.service.inquiry;

import com.hyeobjin.application.admin.dto.inquiry.FindAdminInquiryDTO;
import com.hyeobjin.application.admin.dto.inquiry.FindAdminInquiryDetailDTO;
import com.hyeobjin.application.admin.service.file.AdminInquiryFileService;
import com.hyeobjin.domain.entity.inquiry.Inquiry;
import com.hyeobjin.domain.repository.inquiry.InquiryRepository;
import com.hyeobjin.domain.repository.inquiry.InquiryRepositoryImpl;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class AdminInquiryService {

    private final AdminInquiryFileService adminInquiryFileService;
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

    public List<FindAdminInquiryDTO> findSimple() {

        List<Inquiry> result = inquiryRepository.findTop2ByOrderByCreateAtDesc();

       return result.stream().map(inquiry -> new FindAdminInquiryDTO(
                inquiry.getId(),
                inquiry.getTitle(),
                inquiry.getWriter(),
                inquiry.getCreateAt()
        )).collect(Collectors.toList());
    }

    @Transactional
    public void delete(Long inquiryId) {

        boolean existsById = inquiryRepository.existsById(inquiryId);

        if (!existsById) {
            log.info("문의 내용이 존재 하지 않습니다.");
            return;
        }
            Inquiry inquiry = inquiryRepository.findById(inquiryId).orElseThrow(
                    () -> new EntityNotFoundException("해당 문의 내용을 찾는 도중 오류가 발생 하였습니다."));

            adminInquiryFileService.deleteStaticFiles(inquiry.getId());

            inquiryRepository.delete(inquiry);
    }
}
