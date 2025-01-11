package com.hyeobjin.domain.entity.board;

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

    @Column(name = "board_content")
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
    private Boolean boardYN;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "users_id")
    private Users users;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "filebox_id")
    private FileBox fileBox;
}
