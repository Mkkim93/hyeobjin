package com.hyeobjin.domain.entity.item;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "item_type")
@Getter
@NoArgsConstructor
public class ItemType {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "type_name")
    private String typeName;

    public ItemType(String typeName) {
        this.typeName = typeName;
    }

    public void updateTypeName(String typeName) {
        this.typeName = typeName;
    }

    @Builder
    public ItemType(Long id, String typeName) {
        this.id = id;
        this.typeName = typeName;
    }
}
