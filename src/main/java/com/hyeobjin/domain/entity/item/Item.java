package com.hyeobjin.domain.entity.item;

import com.hyeobjin.application.dto.item.UpdateItemDTO;
import com.hyeobjin.domain.entity.manufacturer.Manufacturer;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import static jakarta.persistence.GenerationType.IDENTITY;

@Entity
@Table(name = "item")
@Getter
@NoArgsConstructor
public class Item {

    @Id @GeneratedValue(strategy = IDENTITY)
    private Long id;

    @Column(name = "item_num")
    private String itemNum;

    @Column(name = "item_name")
    private String itemName;

    @Column(name = "item_use")
    private String itemUse;

    @Column(name = "item_spec")
    private String itemSpec;

    @Column(name = "item_in_color")
    private String itemInColor;

    @Column(name = "item_out_color")
    private String itemOutColor;

    @Column(name = "item_frame_width")
    private String itemFrameWidth;

    @Column(name = "item_type")
    private String itemType;

    @Column(name = "item_description", columnDefinition = "text")
    private String itemDescription;

    @Column(name = "item_yn")
    private String itemYN;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.MERGE)
    @JoinColumn(name = "manu_id")
    private Manufacturer manufacturer;

    @Builder
    public Item(Long itemId, String itemNum, String itemName,
                String itemUse, String itemSpec, String itemInColor, String itemOutColor, String itemFrameWidth,
                String itemType, String itemDescription, String itemYN, Long manufacturerId) {
        this.id = itemId;
        this.itemNum = itemNum;
        this.itemName = itemName;
        this.itemUse = itemUse;
        this.itemSpec = itemSpec;
        this.itemInColor = itemInColor;
        this.itemOutColor = itemOutColor;
        this.itemFrameWidth = itemFrameWidth;
        this.itemType = itemType;
        this.itemDescription = itemDescription;
        this.itemYN = itemYN;
        this.manufacturer = Manufacturer.builder()
                .manuId(manufacturerId)
                .build();
    }

    public void setManufacturerByCreateItem(Long manufacturerId) {
        this.manufacturer = Manufacturer.builder()
                .manuId(manufacturerId)
                .build();
    }

    public void setItemIdFromDto(UpdateItemDTO updateItemDTO) {
        this.id = updateItemDTO.getItemId();
    }
}
