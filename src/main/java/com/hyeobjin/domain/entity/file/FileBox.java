package com.hyeobjin.domain.entity.file;

import com.hyeobjin.domain.entity.board.Board;
import com.hyeobjin.domain.entity.item.Item;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
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
@ToString(exclude = {"board", "item"})
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

    @Column(name = "is_main") // 제품의 파일의 메인 사진 구분을 위한 컬럼 추가 (01/26)
    private Boolean isMain;

    @ManyToOne(fetch = LAZY, optional = false)
    @JoinColumn(name = "item_id")  // 외래키 컬럼을 지정
    private Item item;

    @ManyToOne(fetch = LAZY, optional = false)
    @JoinColumn(name = "board_id")
    private Board board;

    @Builder
    public FileBox(String fileOrgName, String fileName,
                   String filePath, String fileType, Long fileSize, Boolean isMain,
                   Item itemId, Board boardId) {
        this.fileOrgName = fileOrgName;
        this.fileName = fileName;
        this.filePath = filePath;
        this.fileSize = fileSize;
        this.fileType = fileType;
        this.isMain = isMain;
        this.item = itemId;
        this.board = boardId;
    }
}
