package com.hyeobjin.domain.repository;

import com.hyeobjin.application.dto.file.CreateFileBoxDTO;
import com.hyeobjin.application.dto.file.FindFileBoxDTO;
import com.hyeobjin.application.dto.item.FindByItemDTO;
import com.hyeobjin.domain.entity.*;
import com.querydsl.jpa.impl.JPAQueryFactory;
import jakarta.persistence.EntityManager;
import org.hibernate.Hibernate;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Repository
@Transactional
public class ItemRepositoryImpl extends QuerydslRepositorySupport implements ItemRepositoryCustom {

    private final JPAQueryFactory jpaQueryFactory;

    public ItemRepositoryImpl(EntityManager em) {
        super(Item.class);
        jpaQueryFactory = new JPAQueryFactory(em);
    }

    @Override
    public FindByItemDTO findByItem(String itemNum) {

        QItem item = QItem.item;
        QFileBox fileBox = QFileBox.fileBox;

        List<FindFileBoxDTO> fileBoxes = jpaQueryFactory
                .selectFrom(fileBox)
                .leftJoin(fileBox.item, item).fetchJoin()
                .leftJoin(item.manufacturer, QManufacturer.manufacturer).fetchJoin()
                .where(item.itemNum.eq(itemNum))
                .fetch()
                // TODO 파일과 함께 조회 시 불필요한 데이터 제거
                .stream().map(FindFileBoxDTO::new)
                .collect(Collectors.toList());

        Item selectItem = jpaQueryFactory
                .selectFrom(item)
                .leftJoin(item.manufacturer, QManufacturer.manufacturer)
                .where(item.itemNum.eq(itemNum))
                .fetchOne();

        return new FindByItemDTO(
                selectItem.getId(),
                selectItem.getItemName(),
                fileBoxes,
                selectItem.getItemNum());
    }
}
