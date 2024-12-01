package com.mbarek0.web.huntersleague.model;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;
import java.util.UUID;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "participations")
public class Participation{

    public Participation(Competition competition, User user, Double score) {
        this.competition = competition;
        this.user = user;
        this.score = score;
    }

    @Id @GeneratedValue(strategy =  GenerationType.UUID)
    private UUID id;

    @ManyToOne
    private User user;

    @ManyToOne
    private Competition competition;

    @OneToMany(mappedBy = "participation")
    private List<Hunt> hunts;

    private Double score;

    @Column(name = "deleted", nullable = false)
    private boolean deleted = false;

}