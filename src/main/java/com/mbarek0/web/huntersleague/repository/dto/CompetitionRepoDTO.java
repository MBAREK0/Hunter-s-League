package com.mbarek0.web.huntersleague.repository.dto;

import java.time.LocalDateTime;
import java.util.UUID;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CompetitionRepoDTO {


    private UUID id;
    private String location;
    private LocalDateTime date;
    private Integer participations;

}
