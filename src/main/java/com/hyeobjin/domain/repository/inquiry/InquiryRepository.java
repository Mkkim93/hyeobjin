package com.hyeobjin.domain.repository.inquiry;

import com.hyeobjin.domain.entity.inquiry.Inquiry;
import org.springframework.data.jpa.repository.JpaRepository;

public interface InquiryRepository extends JpaRepository<Inquiry, Long> {
}

