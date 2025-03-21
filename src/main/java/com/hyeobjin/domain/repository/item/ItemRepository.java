package com.hyeobjin.domain.repository.item;

import com.hyeobjin.application.common.dto.item.FindByItemDTO;
import com.hyeobjin.application.common.dto.item.FindItemNameDTO;
import com.hyeobjin.domain.entity.item.Item;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


public interface ItemRepository extends JpaRepository<Item, Long> {

    @Transactional
    @Modifying
    @Query("update Item i set i.itemYN = TRUE where i.id = :itemId")
    Integer updateItemYN(@Param("itemId") Long itemId);

    @Query("select new com.hyeobjin.application.common.dto.item.FindByItemDTO(i.id, i.itemNum, i.manufacturer.id) from Item i where i.manufacturer.id = :manuId and i.itemYN = TRUE")
    List<FindByItemDTO> findAllItemId(@Param("manuId") Long manuId);

    @Transactional
    void deleteAllByIdIn(List<Long> list);

    @Transactional
    @Modifying
    @Query("update Item i set i.itemYN = :itemYN where i.id = :itemId")
    Long updateYN(@Param("itemId") Long itemId, @Param("itemYN") Boolean itemYN);

    @Query("select new com.hyeobjin.application.common.dto.item.FindItemNameDTO(i.id, i.itemName) from Item i " +
            "left join Manufacturer m on m.id = i.manufacturer.id " +
            "left join ItemType t on t.id = i.itemType.id " +
            "where m.id = :manuId and t.id = :typeId")
    List<FindItemNameDTO> findItemNum(@Param("manuId") Long manuId, @Param("typeId") Long typeId);
}
