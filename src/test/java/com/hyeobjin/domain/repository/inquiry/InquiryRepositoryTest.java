package com.hyeobjin.domain.repository.inquiry;

import com.hyeobjin.application.admin.dto.inquiry.FindAdminInquiryDetailDTO;
import com.hyeobjin.domain.entity.inquiry.Inquiry;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@Slf4j
@SpringBootTest
class InquiryRepositoryTest {

    @Autowired
    InquiryRepository inquiryRepository;

    @Autowired
    InquiryRepositoryImpl inquiryRepositoryImpl;


    @Test
    void findDetailQueryDsl() {
        Long inquiryId = 1L;
        FindAdminInquiryDetailDTO detail = inquiryRepositoryImpl.findDetail(inquiryId);
        System.out.println("detail = " + detail);
    }

    @Test
    @DisplayName("최근 2건의 문의사항 조회")
    void findTop2Desc() {
        List<Inquiry> result = inquiryRepository.findTop2ByOrderByCreateAtDesc();

        result.stream().forEach(System.out::println);
    }

}