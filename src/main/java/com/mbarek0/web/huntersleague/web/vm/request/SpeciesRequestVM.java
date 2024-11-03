package com.mbarek0.web.huntersleague.web.vm.request;

import com.mbarek0.web.huntersleague.model.enums.Difficulty;
import com.mbarek0.web.huntersleague.model.enums.SpeciesType;
import jakarta.validation.constraints.*;
import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SpeciesRequestVM {

    @NotBlank(message = "Name is required.")
    private String name;

    @NotNull(message = "Category is required.")
    private SpeciesType category;

    @NotNull(message = "Minimum weight is required.")
    @DecimalMin(value = "0.0", message = "Minimum weight must be greater than or equal to 0.0.")
    private Double minimumWeight;

    @NotNull(message = "Difficulty is required.")
    private Difficulty difficulty;

    @NotNull(message = "Points are required.")
    @Min(value = 0, message = "Points must be greater than or equal to 0.")
    private Integer points;
}
