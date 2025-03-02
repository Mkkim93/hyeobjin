package com.hyeobjin.domain.repository.item;

import com.hyeobjin.application.admin.dto.file.FindAdminFileBoxDTO;
import com.hyeobjin.application.admin.dto.item.*;
import com.hyeobjin.application.common.dto.file.FindFileBoxDTO;
import com.hyeobjin.application.common.dto.item.FindByItemDTO;
import com.hyeobjin.domain.entity.file.QFileBox;
import com.hyeobjin.domain.entity.item.*;
import com.hyeobjin.domain.entity.manufacturer.Manufacturer;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.Projections;
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

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import static com.hyeobjin.domain.entity.item.QItem.item;
import static com.hyeobjin.domain.entity.item.QItemType.*;
import static com.hyeobjin.domain.entity.manufacturer.QManufacturer.manufacturer;

@Repository
@Transactional
public class ItemRepositoryImpl extends QuerydslRepositorySupport implements ItemRepositoryCustom {

    private final EntityManager entityManager;
    private final JPAQueryFactory jpaQueryFactory;

    public ItemRepositoryImpl(EntityManager entityManager) {
        super(Item.class);
        this.entityManager = entityManager;
        this.jpaQueryFactory = new JPAQueryFactory(entityManager);
    }

    @Override
    public FindAdminDetailDTO findItemDetail(Long itemId) {

        QItem item = QItem.item;
        QFileBox fileBox = QFileBox.fileBox;

        FindAdminFileBoxDTO findAdminFileBoxDTO =
                jpaQueryFactory.select(Projections.constructor(
                        FindAdminFileBoxDTO.class,
                        fileBox.id, fileBox.fileName, fileBox.fileOrgName,
                        fileBox.fileSize, fileBox.fileType, fileBox.filePath, fileBox.isMain
                )).from(fileBox)
                .leftJoin(fileBox.item, item) // DTO 를 조회할 떄는 fetchJoin 를 사용하면 안된다
                .where(item.id.eq(itemId))
                .orderBy(fileBox.id.asc()).fetchFirst();


        // 테이블 분리 : ItemType, GlassSpec
        // Join 2 추가
        // TODO 향후 어떻게 최적화 해야 할지 고민
        Item selectItem = jpaQueryFactory
                .selectFrom(item)
                .leftJoin(item.manufacturer, manufacturer)
                .leftJoin(QItem.item.itemType, itemType)
                .leftJoin(QItem.item.glassSize, QGlassSpec.glassSpec1)
                .where(item.id.eq(itemId))
                .fetchOne();

        return new FindAdminDetailDTO(
                selectItem.getId(),
                selectItem.getItemName(),
                selectItem.getItemNum(),
                selectItem.getItemUse(),
                selectItem.getItemInColor(),
                selectItem.getItemOutColor(),
                selectItem.getItemFrameWidth(),
                selectItem.getItemDescription(),
                selectItem.getItemRegDate(),
                selectItem.getItemUpdate(),
                selectItem.getItemYN(),
                selectItem.getFreeContent(),
                selectItem.getManufacturer().getId(),
                selectItem.getManufacturer().getManuName(),
                selectItem.getItemType().getTypeName(),
                selectItem.getGlassSize().getGlassSpec(),
                findAdminFileBoxDTO
        );
    }

    @Override
    public Page<FindAdminItemDTO> findItemList(Pageable pageable, String manuName) {

        BooleanBuilder builder = new BooleanBuilder();

        if (manuName != null && !manuName.isEmpty()) {
            builder.and(manufacturer.manuName.eq(manuName));
        }

        List<FindAdminItemDTO> results = jpaQueryFactory.select(new QFindAdminItemDTO(
                        item.id, item.itemNum, item.itemName,
                        item.itemRegDate, item.itemUpdate, item.itemYN, itemType.typeName,
                        manufacturer.id, manufacturer.manuName))
                .from(item)
                .leftJoin(item.manufacturer, manufacturer)
                .leftJoin(item.itemType, itemType)
                .where(builder)
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize()).fetch();

        JPAQuery<Long> count = jpaQueryFactory.select(item.count())
                .from(item)
                .leftJoin(item.manufacturer, manufacturer)
                .leftJoin(item.itemType, itemType)
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
                        item.id.eq(itemId),
                        (item.manufacturer.id.eq(manuId)),
                        (item.itemYN.eq(true))
                )
                .fetch()
                .stream().map(FindFileBoxDTO::new)
                .collect(Collectors.toList());

        Item selectItem = jpaQueryFactory
                .selectFrom(item)
                .leftJoin(item.manufacturer, manufacturer)
                .leftJoin(item.itemType, itemType)
                .leftJoin(item.glassSize, QGlassSpec.glassSpec1)
                .where(
                        item.id.eq(itemId),
                        item.itemYN.eq(true))
                .fetchOne();

        return new FindByItemDTO(
                selectItem.getId(),
                selectItem.getItemName(),
                selectItem.getItemNum(),
                selectItem.getItemUse(),
                selectItem.getItemInColor(),
                selectItem.getItemOutColor(),
                selectItem.getItemFrameWidth(),
                selectItem.getItemDescription(),
                selectItem.getFreeContent(),
                selectItem.getItemType().getTypeName(),
                selectItem.getGlassSize().getGlassSpec(),
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
        if (updateItemDTO.getGlassSpecId() != null) {
            updateClause.set(item.glassSize.id, updateItemDTO.getGlassSpecId());
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

        // TODO 문제 발생 가능성 있음
        if (updateItemDTO.getItemTypeId() != null) {
            updateClause.set(item.itemType.id, updateItemDTO.getItemTypeId());
        }

        if (updateItemDTO.getFreeContent() != null) {
            updateClause.set(item.freeContent, updateItemDTO.getFreeContent());
        }
            updateClause.set(item.itemUpdate, LocalDateTime.now());

        updateClause.where(item.id.eq(updateItemDTO.getItemId()));

        long updateCount = updateClause.execute();

        if (updateCount > 0) {
            // TODO 굳이 데이터를 반환할 필요가 없을 것 같다
            return new UpdateItemDTO(
                    updateItemDTO.getItemId(),
                    updateItemDTO.getItemNum(),
                    updateItemDTO.getItemName(),
                    updateItemDTO.getIsMain(),
                    updateItemDTO.getItemUse(),
                    updateItemDTO.getItemInColor(),
                    updateItemDTO.getItemOutColor(),
                    updateItemDTO.getItemFrameWidth(),
                    updateItemDTO.getItemDescription(),
                    updateItemDTO.getItemYN(),
                    updateItemDTO.getItemUpdate(),
                    updateItemDTO.getFreeContent(),
                    updateItemDTO.getManuId(),
                    updateItemDTO.getManuName(),
                    updateItemDTO.getItemTypeId(),
                            updateItemDTO.getGlassSpecId());
        } else {
            throw new EntityNotFoundException("해당 제품을 찾을 수 없습니다.");
        }
    }

}
