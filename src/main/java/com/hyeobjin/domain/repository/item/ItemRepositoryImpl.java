package com.hyeobjin.domain.repository.item;

import com.hyeobjin.application.admin.dto.item.FindAdminItemDTO;
import com.hyeobjin.application.admin.dto.item.QFindAdminItemDTO;
import com.hyeobjin.application.common.dto.file.FindFileBoxDTO;
import com.hyeobjin.application.common.dto.item.FindByItemDTO;
import com.hyeobjin.application.common.dto.item.UpdateItemDTO;
import com.hyeobjin.domain.entity.file.QFileBox;
import com.hyeobjin.domain.entity.item.QItem;
import com.hyeobjin.domain.entity.manufacturer.QManufacturer;
import com.hyeobjin.domain.entity.item.Item;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.querydsl.jpa.impl.JPAUpdateClause;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;
import org.springframework.data.support.PageableExecutionUtils;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

import static com.hyeobjin.domain.entity.item.QItem.item;
import static com.hyeobjin.domain.entity.manufacturer.QManufacturer.manufacturer;

@Repository
@Transactional
public class ItemRepositoryImpl extends QuerydslRepositorySupport implements ItemRepositoryCustom {

    private final JPAQueryFactory jpaQueryFactory;

    public ItemRepositoryImpl(EntityManager em) {
        super(Item.class);
        jpaQueryFactory = new JPAQueryFactory(em);
    }

    @Override
    public Page<FindAdminItemDTO> findItemList(Pageable pageable, String manuName) {

        BooleanBuilder builder = new BooleanBuilder();

        if (manuName != null && !manuName.isEmpty()) {
            builder.and(manufacturer.manuName.eq(manuName));
        }

        List<FindAdminItemDTO> results = jpaQueryFactory.select(new QFindAdminItemDTO(
                        item.id, item.itemNum, item.itemName, item.itemType,
                        item.itemRegDate, item.itemUpdate, item.itemYN,
                        manufacturer.id, manufacturer.manuName))
                .from(item)
                .leftJoin(item.manufacturer, manufacturer)
                .where(builder)
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize()).fetch();

        JPAQuery<Long> count = jpaQueryFactory.select(item.count())
                .from(item)
                .leftJoin(item.manufacturer, manufacturer)
                .where(builder);

        return PageableExecutionUtils.getPage(results, pageable, () -> count.fetchCount());
    }

    @Override
    public FindByItemDTO findByItem(Long manuId, Long itemId) {

        QItem item = QItem.item;
        QFileBox fileBox = QFileBox.fileBox;

        List<FindFileBoxDTO> fileBoxes = jpaQueryFactory
                .selectFrom(fileBox)
                .leftJoin(fileBox.item, item).fetchJoin()
                .leftJoin(item.manufacturer, manufacturer).fetchJoin()
                .where(
                        item.id.eq(itemId)
                                .and(item.manufacturer.id.eq(manuId))
                                .and(item.itemYN.eq("Y"))
                )
                .fetch()
                // TODO 파일과 함께 조회 시 불필요한 데이터 제거
                .stream().map(FindFileBoxDTO::new)
                .collect(Collectors.toList());

        Item selectItem = jpaQueryFactory
                .selectFrom(item)
                .leftJoin(item.manufacturer, manufacturer)
                .where(item.id.eq(itemId))
                .fetchOne();

        return new FindByItemDTO(
                selectItem.getId(),
                selectItem.getItemName(),
                selectItem.getItemNum(),
                selectItem.getItemUse(),
                selectItem.getItemSpec(),
                selectItem.getItemInColor(),
                selectItem.getItemOutColor(),
                selectItem.getItemFrameWidth(),
                selectItem.getItemDescription(),
                selectItem.getItemType(),
                selectItem.getManufacturer().getId(),
                selectItem.getManufacturer().getManuName(),
                fileBoxes
                );
    }

    @Override
    public UpdateItemDTO updateItem(UpdateItemDTO updateItemDTO) {
        EntityManager entityManager = getEntityManager();

        JPAUpdateClause updateClause = new JPAUpdateClause(entityManager ,item);

        if (updateItemDTO.getItemId() != null) {
            updateClause.set(item.id, updateItemDTO.getItemId());
        }
        if (updateItemDTO.getItemNum() != null) {
            updateClause.set(item.itemNum, updateItemDTO.getItemNum());
        }
        if (updateItemDTO.getItemName() != null) {
            updateClause.set(item.itemName, updateItemDTO.getItemName());
        }
        if (updateItemDTO.getItemUse() != null) {
            updateClause.set(item.itemUse, updateItemDTO.getItemUse());
        }
        if (updateItemDTO.getItemSpec() != null) {
            updateClause.set(item.itemSpec, updateItemDTO.getItemSpec());
        }
        if (updateItemDTO.getItemInColor() != null) {
            updateClause.set(item.itemInColor, updateItemDTO.getItemInColor());
        }
        if (updateItemDTO.getItemOutColor() != null) {
            updateClause.set(item.itemOutColor, updateItemDTO.getItemOutColor());
        }
        if (updateItemDTO.getItemFrameWidth() != null) {
            updateClause.set(item.itemFrameWidth, updateItemDTO.getItemFrameWidth());
        }
        if (updateItemDTO.getItemDescription() != null) {
            updateClause.set(item.itemDescription, updateItemDTO.getItemDescription());
        }
        if (updateItemDTO.getItemYN() != null) {
            updateClause.set(item.itemYN, updateItemDTO.getItemYN());
        }
//        if (updateItemDTO.getManuId() != null) {
//            updateClause.set(item.manufacturer.id, updateItemDTO.getManuId());
//        }
//        if (updateItemDTO.getManuName() != null) {
//            updateClause.set(item.manufacturer.manuName, updateItemDTO.getManuName());
//        }
        updateClause.where(item.id.eq(updateItemDTO.getItemId()));

        long updateCount = updateClause.execute();

        if (updateCount > 0) {
            // TODO 굳이 데이터를 반환할 필요가 없을 것 같다
            return new UpdateItemDTO(
                    updateItemDTO.getItemId(),
                    updateItemDTO.getItemNum(),
                    updateItemDTO.getItemName(),
                    updateItemDTO.getItemUse(),
                    updateItemDTO.getItemSpec(),
                    updateItemDTO.getItemInColor(),
                    updateItemDTO.getItemOutColor(),
                    updateItemDTO.getItemFrameWidth(),
                    updateItemDTO.getItemType(),
                    updateItemDTO.getItemDescription(),
                    updateItemDTO.getItemYN(),
                    updateItemDTO.getManuId(),
                    updateItemDTO.getManuName()
                    );
        } else {
            throw new EntityNotFoundException("해당 제품을 찾을 수 없습니다.");
        }
    }

}
