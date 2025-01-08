package com.hyeobjin.domain.entity;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import static jakarta.persistence.GenerationType.*;

@Entity
@Table(name = "manufacturer")
@Getter
@NoArgsConstructor
public class Manufacturer {

    @Id @GeneratedValue(strategy = IDENTITY)
    private Long id;

    @Column(name = "manu_name")
    private String manuName;

    @Builder
    public Manufacturer(Long manuId, String manuName) {
        this.id = manuId;
        this.manuName = manuName;
    }
}
