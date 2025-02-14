package com.hyeobjin.domain.repository.inquiry;

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

}