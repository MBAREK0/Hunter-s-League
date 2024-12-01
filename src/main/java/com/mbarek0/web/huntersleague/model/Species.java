package com.mbarek0.web.huntersleague.model;


import com.mbarek0.web.huntersleague.model.enums.Difficulty;
import com.mbarek0.web.huntersleague.model.enums.SpeciesType;
import jakarta.persistence.*;
import lombok.*;

import java.util.UUID;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Species {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(nullable = false, unique = true)
    private String name;

    @Enumerated(EnumType.STRING)
    private SpeciesType category;

    private Double minimumWeight;

    @Enumerated(EnumType.STRING)
    private Difficulty difficulty;

    private Integer points;

    @Column(name = "deleted", nullable = false)
    private boolean deleted = false;

}