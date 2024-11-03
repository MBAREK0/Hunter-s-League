package com.mbarek0.web.huntersleague.web.vm.response;

import com.mbarek0.web.huntersleague.model.enums.Difficulty;
import com.mbarek0.web.huntersleague.model.enums.SpeciesType;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

import java.util.UUID;


@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SpeciesResponseVM {

    private UUID id;
    private String name;
    private SpeciesType category;
    private Double minimumWeight;
    private Difficulty difficulty;
    private Integer points;
}
