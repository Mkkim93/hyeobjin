package com.hyeobjin.domain.repository.item;

import com.hyeobjin.application.common.dto.item.type.FindItemTypeDTO;
import com.hyeobjin.domain.entity.item.ItemType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ItemTypeRepository extends JpaRepository<ItemType, Long> {

    @Query("select new com.hyeobjin.application.common.dto.item.type.FindItemTypeDTO(t.id, t.typeName, i.id, i.itemName) " +
            "from ItemType t left join Item i on t.id = i.itemType.id where " +
            "i.itemType.id = :itemTypeId and i.manufacturer.id = :manuId and i.itemYN = true order by t.id desc")
    List<FindItemTypeDTO> findByItemNameList(@Param("itemTypeId") Long itemTypeId, @Param("manuId") Long manuId);

    @Query("select distinct new com.hyeobjin.application.common.dto.item.type.FindItemTypeDTO(t.id, t.typeName, i.id, i.itemName) " +
            "from ItemType t join Item i " +
            "where i.manufacturer.id = :manuId")
    List<FindItemTypeDTO> findByItemNames(@Param("manuId") Long manuId);
}
