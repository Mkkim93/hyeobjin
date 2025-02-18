package com.hyeobjin.application.common.service.inquiry;

import com.hyeobjin.application.common.dto.inquriy.CreateInquiryDTO;
import com.hyeobjin.application.common.service.inquiry.file.InquiryFileService;
import com.hyeobjin.application.common.service.inquiry.mail.InquiryMailService;
import com.hyeobjin.domain.entity.inquiry.Inquiry;
import com.hyeobjin.domain.repository.inquiry.InquiryRepository;
import jakarta.mail.MessagingException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Slf4j
@Service
public class InquiryService {

    private final InquiryRepository inquiryRepository;
    private final InquiryFileService inquiryFileService;
    private final InquiryMailService inquiryMailService;

    @Autowired
    public InquiryService(InquiryRepository inquiryRepository, InquiryFileService inquiryFileService, InquiryMailService inquiryMailService) {
        this.inquiryRepository = inquiryRepository;
        this.inquiryFileService = inquiryFileService;
        this.inquiryMailService = inquiryMailService;
    }

    @Transactional
    public void save(CreateInquiryDTO createInquiryDTO, List<MultipartFile> saveFiles) throws MessagingException {

        Inquiry inquiry = createInquiryDTO.toEntity(createInquiryDTO);
        Inquiry saved = inquiryRepository.save(inquiry);

        if (saveFiles != null && !saveFiles.isEmpty()) {
            try {
                inquiryFileService.fileSave(saved, saveFiles);
            } catch (Exception e) {
                throw new RuntimeException("파일 저장 실패", e);
            }
        }

        inquiryMailService.sendEmail(saved.getContent(), saved.getTitle());

    }
}
