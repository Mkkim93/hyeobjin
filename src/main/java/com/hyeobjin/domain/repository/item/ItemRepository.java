package com.hyeobjin.domain.repository.item;

import com.hyeobjin.domain.entity.item.Item;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ItemRepository extends JpaRepository<Item, Long> {
}
