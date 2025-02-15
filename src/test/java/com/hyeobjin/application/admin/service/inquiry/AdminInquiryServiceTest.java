package com.hyeobjin.application.admin.service.inquiry;

import com.hyeobjin.application.admin.dto.inquiry.FindAdminInquiryDTO;
import com.hyeobjin.application.admin.dto.inquiry.FindAdminInquiryDetailDTO;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@Slf4j
@SpringBootTest
class AdminInquiryServiceTest {

    @Autowired
    AdminInquiryService adminInquiryService;

    @Test
    @DisplayName("관리자 문의 내용 상세조회")
    void findDetail() {

        Long inquiryId = 1L;

        FindAdminInquiryDetailDTO detailWithFiles = adminInquiryService.findDetailWithFiles(inquiryId);

        System.out.println(detailWithFiles);
    }

    @Test
    @DisplayName("관리자 문의 내용 최신 2개 조회")
    void findTop2() {
        List<FindAdminInquiryDTO> simple = adminInquiryService.findSimple();

        simple.stream().forEach(System.out::println);
    }
}