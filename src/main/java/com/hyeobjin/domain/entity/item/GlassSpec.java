package com.hyeobjin.domain.entity.item;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "item_glass_spec")
@Getter
@NoArgsConstructor
public class GlassSpec {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "glass_spec")
    private String glassSpec;

    public GlassSpec(String glassSpec) {
        this.glassSpec = glassSpec;
    }

    public void updateGlassSpec(String glassSpec) {
        this.glassSpec = glassSpec;
    }

    @Builder
    public GlassSpec(Long id, String glassSpec) {
        this.id = id;
        this.glassSpec = glassSpec;
    }
}
