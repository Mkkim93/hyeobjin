package com.hyeobjin.domain.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

import java.time.LocalDateTime;

import static jakarta.persistence.GenerationType.*;

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
}
