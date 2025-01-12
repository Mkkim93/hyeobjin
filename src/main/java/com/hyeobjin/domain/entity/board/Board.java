package com.hyeobjin.domain.entity.board;

import com.hyeobjin.application.dto.board.CreateBoardDTO;
import com.hyeobjin.domain.entity.file.FileBox;
import com.hyeobjin.domain.entity.users.Users;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

import java.time.LocalDateTime;
import java.util.List;

import static jakarta.persistence.GenerationType.IDENTITY;

@EntityListeners(AuditingEntityListener.class)
@EnableJpaAuditing
@Entity
@Table(name = "board")
@Getter
@NoArgsConstructor
public class Board {

    @Id @GeneratedValue(strategy = IDENTITY)
    private Long id;

    @Column(name = "board_pass")
    private String boardPass;

    @Column(name = "board_title")
    private String boardTitle;

    @Column(name = "board_content", columnDefinition = "text")
    private String boardContent;

    @Column(name = "board_viewcount")
    private Long boardViewCount;

    @Column(name = "board_type")
    private String boardType;

    @CreatedDate
    @Column(name = "board_regdate", updatable = false)
    private LocalDateTime boardRegDate;

    @LastModifiedDate
    @Column(name = "board_update")
    private LocalDateTime boardUpdate;

    @Column(name = "board_yn")
    private String boardYN;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "users_id")
    private Users users;

    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL, mappedBy = "board")
    private List<FileBox> fileBox;

    public Board saveToEntity(CreateBoardDTO createBoardDTO) {
        this.id = createBoardDTO.getBoardId();
        this.boardTitle = createBoardDTO.getBoardTitle();
        this.boardContent = createBoardDTO.getBoardContent();
        this.boardYN = "N";
        this.boardViewCount = 0L;
        this.users = Users.builder()
                .userId(createBoardDTO.getUsersId())
                .build();
        return this;
    }
}
