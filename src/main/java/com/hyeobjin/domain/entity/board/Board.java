package com.hyeobjin.domain.entity.board;

import com.hyeobjin.application.common.dto.board.BoardFileDTO;
import com.hyeobjin.application.common.dto.board.CreateBoardDTO;
import com.hyeobjin.domain.entity.users.Users;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

import java.time.LocalDateTime;

import static jakarta.persistence.GenerationType.IDENTITY;


@Entity
@Table(name = "board")
@Getter
@NoArgsConstructor
@EnableJpaAuditing
@EntityListeners(AuditingEntityListener.class)
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

    public Board saveToEntity(CreateBoardDTO createBoardDTO) {
        this.id = createBoardDTO.getBoardId();
        this.boardTitle = createBoardDTO.getBoardTitle();
        this.boardContent = createBoardDTO.getBoardContent();
        this.boardType = createBoardDTO.getBoardType();
        this.boardYN = createBoardDTO.getBoardYN();
        this.boardViewCount = 0L;
        this.users = Users.builder()
                .userId(createBoardDTO.getUsersId())
                .build();
        return this;
    }

    @Builder
    public Board(Long boardId, String boardTitle,
                 String boardContent, String boardYN, Long boardViewCount, Long userId) {
        this.id = boardId;
        this.boardTitle = boardTitle;
        this.boardContent = boardContent;
        this.boardYN = "N";
        this.boardViewCount = 0L;
        this.users = Users.builder()
                .userId(userId)
                .build();
    }
}
