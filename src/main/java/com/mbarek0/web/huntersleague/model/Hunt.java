package com.mbarek0.web.huntersleague.model;

import jakarta.persistence.*;
import lombok.*;

import java.util.UUID;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "hunts")
public class Hunt {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @ManyToOne
    private Species species;

    private Double weight;

    @ManyToOne
    private Participation participation;

    @Column(name = "deleted", nullable = false)
    private boolean deleted = false;
}