package com.hyeobjin.domain.repository.inquiry;

import com.hyeobjin.application.admin.dto.inquiry.FindAdminInquiryDetailDTO;
import com.hyeobjin.domain.entity.inquiry.Inquiry;
import lombok.extern.slf4j.Slf4j;
import org.assertj.core.api.AssertionsForClassTypes;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@Slf4j
@DataJpaTest
@Transactional
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@DisplayName("1:1 문의 테스트")
class InquiryRepositoryTest {

    @Autowired
    private InquiryRepository inquiryRepository;

    @Autowired
    private InquiryRepositoryImpl inquiryRepositoryImpl;


    @Test
    @DisplayName("문의 내용 상세 조회")
    void findDetailQueryDsl() {

        // given
        Long inquiryId = 1L;

        // when
        FindAdminInquiryDetailDTO detail = inquiryRepositoryImpl.findDetail(inquiryId);

        // then
        assertThat(detail.getInquiryId()).isEqualTo(inquiryId);
    }

    @Test
    @DisplayName("최근 1건 이상의 간편 문의사항 조회")
    void findTop2Desc() {

        // when
        List<Inquiry> result = inquiryRepository.findTop2ByOrderByCreateAtDesc();

        // then
        AssertionsForClassTypes.assertThat(result).isNotNull();
        assertThat(result.size()).isLessThanOrEqualTo(2);

        result.stream().forEach(System.out::println);
    }

}