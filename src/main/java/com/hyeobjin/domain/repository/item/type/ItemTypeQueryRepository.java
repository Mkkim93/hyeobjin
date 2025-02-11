package com.hyeobjin.domain.repository.item.type;

import com.hyeobjin.application.common.dto.item.type.ItemTypeDTO;
import com.hyeobjin.domain.entity.item.ItemType;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class ItemTypeQueryRepository {

    @PersistenceContext
    private final EntityManager em;

    public List<ItemTypeDTO> findByCategoryByManuId(Long manuId) {

        return em.createQuery("select distinct t.id, t.typeName from ItemType t " +
                "left join Item i on t.id = i.itemType.id " +
                "where i.manufacturer.id = : manuId", ItemTypeDTO.class)
                .setParameter("manuId", manuId).getResultList();
    }
}

