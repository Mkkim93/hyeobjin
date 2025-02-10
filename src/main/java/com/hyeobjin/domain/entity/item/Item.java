package com.hyeobjin.domain.entity.item;

import com.hyeobjin.domain.entity.manufacturer.Manufacturer;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

import static jakarta.persistence.GenerationType.IDENTITY;

@Entity
@Table(name = "item")
@Getter
@EntityListeners(AuditingEntityListener.class)
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

    @Column(name = "item_in_color")
    private String itemInColor;

    @Column(name = "item_out_color")
    private String itemOutColor;

    @Column(name = "item_frame_width")
    private String itemFrameWidth;

    @Column(name = "item_description", columnDefinition = "text")
    private String itemDescription;

    @Column(name = "item_yn")
    private String itemYN;

    @CreatedDate
    @Column(name = "item_regdate", updatable = false)
    private LocalDateTime itemRegDate;

    @LastModifiedDate
    @Column(name = "item_update")
    private LocalDateTime itemUpdate;

    @Column(name = "freecontent", columnDefinition = "text")
    private String freeContent;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.MERGE)
    @JoinColumn(name = "manu_id")
    private Manufacturer manufacturer;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "glass_size")
    private GlassSpec glassSize;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "item_type")
    private ItemType itemType;

    @Builder
    public Item(Long itemId, String itemNum, String itemName,
                String itemUse, String itemInColor, String itemOutColor, String itemFrameWidth,
                 String itemDescription, String itemYN,
                LocalDateTime itemRegDate, LocalDateTime itemUpdate, String freeContent,
                Long manufacturerId, Long glassSpecId, Long itemTypeId) {
        this.id = itemId;
        this.itemNum = itemNum;
        this.itemName = itemName;
        this.itemUse = itemUse;
        this.itemInColor = itemInColor;
        this.itemOutColor = itemOutColor;
        this.itemFrameWidth = itemFrameWidth;
        this.itemDescription = itemDescription;
        this.itemYN = itemYN;
        this.itemRegDate = itemRegDate;
        this.itemUpdate = itemUpdate;
        this.freeContent = freeContent;

        this.manufacturer = Manufacturer.builder()
                .manuId(manufacturerId)
                .build();

        this.glassSize = GlassSpec.builder()
                .id(glassSpecId)
                .build();

        this.itemType = ItemType.builder()
                .id(itemTypeId)
                .build();
    }

    public void setManufacturerByCreateItem(Long manufacturerId) {
        this.manufacturer = Manufacturer.builder()
                .manuId(manufacturerId)
                .build();
    }
}
