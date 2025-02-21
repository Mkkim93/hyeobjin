package com.hyeobjin.domain.repository.inquiry;

import com.hyeobjin.application.admin.dto.inquiry.FindAdminInquiryDetailDTO;
import com.hyeobjin.domain.entity.inquiry.Inquiry;
import lombok.extern.slf4j.Slf4j;
import org.assertj.core.api.AssertionsForClassTypes;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.List;

import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@Slf4j
@DataJpaTest
class InquiryRepositoryTest {

    @Autowired
    InquiryRepository inquiryRepository;

    @Autowired
    InquiryRepositoryImpl inquiryRepositoryImpl;


    @Test
    @DisplayName("문의 내용 상세 조회")
    void findDetailQueryDsl() {
        Long inquiryId = 1L;
        FindAdminInquiryDetailDTO detail = inquiryRepositoryImpl.findDetail(inquiryId);
        System.out.println("detail = " + detail);
    }

    @Test
    @DisplayName("최근 2건 간편 문의사항 조회")
    void findTop2Desc() {
        List<Inquiry> result = inquiryRepository.findTop2ByOrderByCreateAtDesc();

        AssertionsForClassTypes.assertThat(result).isNotNull();
        assertThat(result).hasSize(2);

        result.stream().forEach(System.out::println);

    }

}