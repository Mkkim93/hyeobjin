package com.hyeobjin.domain.repository.item;

import com.hyeobjin.domain.entity.item.Item;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;


public interface ItemRepository extends JpaRepository<Item, Long> {

    @Transactional
    @Modifying
    @Query("update Item i set i.itemYN = 'Y' where i.id = :itemId")
    Integer updateItemYN(@Param("itemId") Long itemId);
}
