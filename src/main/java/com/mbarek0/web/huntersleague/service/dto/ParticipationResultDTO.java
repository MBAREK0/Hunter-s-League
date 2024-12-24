package com.mbarek0.web.huntersleague.service.dto;

import com.mbarek0.web.huntersleague.model.enums.SpeciesType;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ParticipationResultDTO {
    private String competitionCode;
    @Enumerated(EnumType.STRING)
    private SpeciesType speciesType;
    private Double score;
}
