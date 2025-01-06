package com.hyeobjin.domain.repository;

import com.hyeobjin.domain.entity.Item;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ItemRepository extends JpaRepository<Item, Long> {
}
