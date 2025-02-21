package com.hyeobjin.domain.entity.item;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "item_glass_spec")
@Getter
@Setter
@NoArgsConstructor
public class GlassSpec {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "glass_spec")
    private String glassSpec;

    public GlassSpec(String glassSpec) {
        this.glassSpec = glassSpec;
    }

    public void updateGlassSpec(Long glassSpecId, String glassSpec) {
        this.id = glassSpecId;
        this.glassSpec = glassSpec;
    }

    @Builder
    public GlassSpec(Long id, String glassSpec) {
        this.id = id;
        this.glassSpec = glassSpec;
    }
}
