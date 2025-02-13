package com.hyeobjin.application.common.service.inquiry;

import com.hyeobjin.application.common.dto.inquriy.CreateInquiryDTO;
import com.hyeobjin.application.common.service.inquiry.file.InquiryFileService;
import com.hyeobjin.domain.entity.inquiry.Inquiry;
import com.hyeobjin.domain.repository.inquiry.InquiryRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class InquiryService {

    private final InquiryRepository inquiryRepository;
    private final InquiryFileService inquiryFileService;

    @Transactional
    public void save(CreateInquiryDTO createInquiryDTO, List<MultipartFile> saveFiles) {

        Inquiry inquiry = createInquiryDTO.toEntity(createInquiryDTO);
        Inquiry saved = inquiryRepository.save(inquiry);

        if (saveFiles != null && !saveFiles.isEmpty()) {
            try {
                inquiryFileService.fileSave(saved, saveFiles);
            } catch (Exception e) {
                throw new RuntimeException("파일 저장 실패", e);
            }
        }
    }

}
