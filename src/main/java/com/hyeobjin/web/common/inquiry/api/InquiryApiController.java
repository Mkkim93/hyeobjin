package com.hyeobjin.web.common.inquiry.api;

import com.hyeobjin.application.common.dto.inquriy.CreateInquiryDTO;
import com.hyeobjin.application.common.service.inquiry.InquiryService;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.mail.MessagingException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Slf4j
@RequestMapping("/inquiry")
@RestController
@Tag(name = "COMMON_INQUIRY", description = "사용자의 1:1문의 관련 API 입니다.")
@RequiredArgsConstructor
public class InquiryApiController {

    private final InquiryService inquiryService;

    @PostMapping
    public ResponseEntity<?> save(@ModelAttribute CreateInquiryDTO createInquiryDTO,
                                  @RequestPart(value = "files", required = false) List<MultipartFile> files) throws MessagingException {

        inquiryService.save(createInquiryDTO, files);

        return ResponseEntity.ok("문의 글 작성이 완료 되었습니다. 최대한 빠르게 답변 드리겠습니다.");
    }
}
