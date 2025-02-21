package com.hyeobjin.domain.repository.inquiry;

import com.hyeobjin.application.admin.dto.file.AdminInquiryFileDTO;
import com.hyeobjin.application.admin.dto.inquiry.FindAdminInquiryDetailDTO;
import com.hyeobjin.domain.entity.file.QFileBox;
import com.hyeobjin.domain.entity.inquiry.Inquiry;
import com.hyeobjin.domain.entity.inquiry.QInquiry;
import com.hyeobjin.domain.entity.item.QItem;
import com.hyeobjin.domain.entity.item.QItemType;
import com.hyeobjin.domain.entity.manufacturer.QManufacturer;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import jakarta.persistence.EntityManager;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
@Transactional
public class InquiryRepositoryImpl extends QuerydslRepositorySupport implements InquiryCustomRepository {

    private final JPAQueryFactory jpaQueryFactory;

    public InquiryRepositoryImpl(EntityManager em) {
        super(Inquiry.class);
        jpaQueryFactory = new JPAQueryFactory(em);
    }

    @Override
    public FindAdminInquiryDetailDTO findDetail(Long inquiryId) {

        QInquiry inquiry = QInquiry.inquiry;
        QFileBox fileBox = QFileBox.fileBox;

        List<AdminInquiryFileDTO> fileList = jpaQueryFactory.select(Projections.constructor(
                        AdminInquiryFileDTO.class,
                        fileBox.id, fileBox.filePath, fileBox.fileOrgName, fileBox.fileName
                )).from(fileBox)
                .join(fileBox.inquiry, inquiry)
                .where(fileBox.inquiry.id.eq(inquiryId)).fetch();

        Inquiry selectInquiry = jpaQueryFactory.selectFrom(inquiry)
                .where(inquiry.id.eq(inquiryId))
                .fetchOne();

        return new FindAdminInquiryDetailDTO(
                selectInquiry.getId(),
                selectInquiry.getTitle(),
                selectInquiry.getContent(),
                selectInquiry.getWriter(),
                selectInquiry.getTel(),
                selectInquiry.getEmail(),
                selectInquiry.getAddr(),
                selectInquiry.getDetailAddr(),
                selectInquiry.getCreateAt(),
                selectInquiry.getManuName(),
                selectInquiry.getItemTypeName(),
                selectInquiry.getItemName(),
                fileList);
    }
}
