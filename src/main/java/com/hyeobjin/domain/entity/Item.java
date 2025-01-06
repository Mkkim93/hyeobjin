package com.hyeobjin.domain.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

import static jakarta.persistence.GenerationType.*;

@Entity
@Table(name = "item")
@Getter
@NoArgsConstructor
public class Item {

    @Id @GeneratedValue(strategy = IDENTITY)
    private Long id;

    @Column(name = "item_name")
    private String itemName;

    @Column(name = "item_use")
    private String itemUse;

    @Column(name = "item_spec")
    private String itemSpec;

    @Lob
    @Column(name = "item_description")
    private String itemDescription;

    @Column(name = "item_yn")
    private Boolean itemYN;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "filebox_id")
    private FileBox fileBox;
}
