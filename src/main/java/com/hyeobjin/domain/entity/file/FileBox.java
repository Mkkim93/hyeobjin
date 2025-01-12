package com.hyeobjin.domain.entity.file;

import com.hyeobjin.domain.entity.board.Board;
import com.hyeobjin.domain.entity.item.Item;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

import java.time.LocalDateTime;

import static jakarta.persistence.FetchType.LAZY;
import static jakarta.persistence.GenerationType.IDENTITY;

@Entity
@Table(name = "filebox")
@Getter
@NoArgsConstructor
@EntityListeners(AuditingEntityListener.class)
@EnableJpaAuditing
public class FileBox {

    @Id @GeneratedValue(strategy = IDENTITY)
    private Long id;

    @Column(name = "file_name")
    private String fileName;

    @Column(name = "file_orgname")
    private String fileOrgName;

    @Column(name = "file_size")
    private Long fileSize;

    @Column(name = "file_type")
    private String fileType;

    @Column(name = "file_path")
    private String filePath;

    @CreatedDate
    @Column(name = "file_regdate")
    private LocalDateTime fileRegDate;

    @ManyToOne(fetch = LAZY, cascade = CascadeType.REMOVE, optional = true)
    @JoinColumn(name = "item_id")  // 외래키 컬럼을 지정
    private Item item;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "board_id")
    private Board board;

    /**
     *
     * @param fileName
     * @param filePath
     * @param fileType
     */
    @Builder
    public FileBox(String fileOrgName, String fileName,
                   String filePath, String fileType, Long fileSize, Long itemId) {
        this.fileOrgName = fileOrgName;
        this.fileName = fileName;
        this.filePath = filePath;
        this.fileSize = fileSize;
        this.fileType = fileType;
        this.item = Item.builder()
                .itemId(itemId)
                .build();
    }
}
