package com.mbarek0.web.huntersleague.web.vm.request;

import com.mbarek0.web.huntersleague.model.enums.Difficulty;
import com.mbarek0.web.huntersleague.model.enums.SpeciesType;
import jakarta.validation.constraints.NotBlank;
import lombok.*;


@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SpeciesVM {

    @NotBlank(message = "Name is required.")
    private String name;

    @NotBlank(message = "Category is required.")
    private SpeciesType category;

    @NotBlank(message = "Minimum weight is required.")
    private Double minimumWeight;

    @NotBlank(message = "Difficulty is required.")
    private Difficulty difficulty;

    @NotBlank(message = "Points are required.")
    private Integer points;
}
