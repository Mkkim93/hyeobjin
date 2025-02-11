package com.hyeobjin.domain.repository.item;

import com.hyeobjin.application.common.dto.item.type.FindItemTypeDTO;
import com.hyeobjin.domain.entity.item.ItemType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ItemTypeRepository extends JpaRepository<ItemType, Long> {

    @Query("select new com.hyeobjin.application.common.dto.item.type.FindItemTypeDTO(t.id, t.typeName, i.id, i.itemName) " +
            "from ItemType t, Item i where t.id = i.itemType.id " +
            "and i.itemType.id = :itemTypeId and i.manufacturer.id = :manuId")
    List<FindItemTypeDTO> findByItemNameList(@Param("itemTypeId") Long itemTypeId, @Param("manuId") Long manuId);

    @Query("SELECT DISTINCT new com.hyeobjin.application.common.dto.item.type.FindItemTypeDTO(t.id, t.typeName, i.id, i.itemName) " +
            "FROM ItemType t JOIN Item i " +
            "WHERE i.manufacturer.id = :manuId")
    List<FindItemTypeDTO> findByItemNames(@Param("manuId") Long manuId);
}
