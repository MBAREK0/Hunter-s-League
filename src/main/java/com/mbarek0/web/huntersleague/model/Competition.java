package com.mbarek0.web.huntersleague.model;

import com.mbarek0.web.huntersleague.model.enums.SpeciesType;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "competitions")
public class Competition {
    @Id
    @GeneratedValue
    private UUID id;

    private String code;

    private String location;

    private LocalDateTime date;

    @Enumerated(EnumType.STRING)
    private SpeciesType speciesType;

    private Integer minParticipants;

    private Integer maxParticipants;

    private Boolean openRegistration;

    @OneToMany(mappedBy = "competition", fetch = FetchType.LAZY)
    private List<Participation> participations;

    @Column(name = "deleted", nullable = false)
    private boolean deleted = false;



}