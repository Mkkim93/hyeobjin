package com.hyeobjin.domain.repository.item;

import com.hyeobjin.domain.entity.item.ItemType;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ItemTypeRepository extends JpaRepository<ItemType, Long> {
}
