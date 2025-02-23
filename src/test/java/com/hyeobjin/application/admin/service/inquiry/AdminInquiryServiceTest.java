package com.hyeobjin.application.admin.service.inquiry;

import com.hyeobjin.application.admin.dto.inquiry.FindAdminInquiryDTO;
import com.hyeobjin.application.admin.dto.inquiry.FindAdminInquiryDetailDTO;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@Slf4j
@Transactional
@DisplayName("관리자 일정 테스트")
@SpringBootTest
class AdminInquiryServiceTest {

    @Autowired
    AdminInquiryService adminInquiryService;

    @Test
    @DisplayName("관리자 문의 내용 상세조회")
    void findDetail() {

        // given
        Long inquiryId = 1L;

        // when
        FindAdminInquiryDetailDTO detailWithFiles = adminInquiryService.findDetailWithFiles(inquiryId);

        // then
        System.out.println(detailWithFiles);
    }

    @Test
    @DisplayName("관리자 문의 내용 최신 2개 조회")
    void findTop2() {
        // when
        List<FindAdminInquiryDTO> simple = adminInquiryService.findSimple();

        // then
        simple.stream().forEach(System.out::println);
    }
}