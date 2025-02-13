package com.hyeobjin.domain.entity.inquiry;

import com.hyeobjin.domain.entity.item.Item;
import com.hyeobjin.domain.entity.item.ItemType;
import com.hyeobjin.domain.entity.manufacturer.Manufacturer;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

@Entity
@Table(name = "inquiry")
@Getter
@NoArgsConstructor
@EntityListeners(AuditingEntityListener.class)
public class Inquiry {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "title")
    private String title;

    @Column(name = "content", columnDefinition = "text")
    private String content;

    @Column(name = "writer")
    private String writer;

    @Column(name = "tel")
    private String tel;

    @Column(name = "email")
    private String email;

    @Column(name = "addr")
    private String addr;

    @Column(name = "detail_addr")
    private String detailAddr;

    @CreatedDate
    @Column(name = "create_at", updatable = false)
    private LocalDateTime createAt;

    @LastModifiedDate
    @Column(name = "update_at")
    private LocalDateTime updateAt;

    @ManyToOne
    @JoinColumn(name = "manu_id")
    private Manufacturer manufacturer;

    @ManyToOne
    @JoinColumn(name = "type_id")
    private ItemType itemType;

    @ManyToOne
    @JoinColumn(name = "item_id")
    private Item item;

    @Builder
    public Inquiry(Long id, String title, String content,
                   String writer, String tel, String email,
                   String addr, String detailAddr, LocalDateTime createAt,
                   Long manuId, Long typeId, Long itemId) {
        this.id = id;
        this.title = title;
        this.content = content;
        this.writer = writer;
        this.tel = tel;
        this.email = email;
        this.addr = addr;
        this.detailAddr = detailAddr;
        this.createAt = createAt;
        this.updateAt = LocalDateTime.now();
        this.manufacturer = Manufacturer.builder()
                .manuId(manuId)
                .build();

        this.itemType = ItemType.builder()
                .id(typeId)
                .build();

        this.item = Item.builder()
                .itemId(itemId)
                .build();
    }
}
