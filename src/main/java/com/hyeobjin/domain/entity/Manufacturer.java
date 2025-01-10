package com.hyeobjin.domain.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Persistent;

import static jakarta.persistence.GenerationType.*;

@Entity
@Table(name = "manufacturer")
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class Manufacturer {

    @Id @GeneratedValue(strategy = IDENTITY)
    private Long id;

    @Column(name = "manu_name")
    private String manuName;

    @Column(name = "manu_yn")
    private String manuYN;

    /**
     * 제조사 등록에만 사용
     * @param manuId
     * @param manuName
     */
    @Builder
    public Manufacturer(Long manuId, String manuName) {
        this.id = manuId;
        this.manuName = manuName;
        this.manuYN = "N";
    }
}
