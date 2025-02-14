package com.hyeobjin.application.common.service.inquiry;

import com.hyeobjin.application.common.dto.inquriy.CreateInquiryDTO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;

@ExtendWith(MockitoExtension.class)
class InquiryServiceTest {

    @InjectMocks
    private InquiryService inquiryService;

    private CreateInquiryDTO createInquiryDTO;
    private List<MultipartFile> fileList;

    @BeforeEach
    void setUp() {
        // DTO 생성
        createInquiryDTO = new CreateInquiryDTO();
        createInquiryDTO.setTitle("문의 제목");
        createInquiryDTO.setContent("문의 내용");
        createInquiryDTO.setManuId(1L);
        createInquiryDTO.setTypeId(1L);
        createInquiryDTO.setItemId(1L);

        // 파일 리스트 생성
        MockMultipartFile mockFile = new MockMultipartFile(
                "file",
                "testFile.jpg",
                "image/jpeg",
                "Test file content".getBytes());

        fileList = new ArrayList<>();
        fileList.add(mockFile);
    }

    @Test
    void saveTest() {
        inquiryService.save(createInquiryDTO, fileList);
    }
}
