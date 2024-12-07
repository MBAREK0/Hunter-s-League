package com.mbarek0.web.huntersleague.web.vm.request;

import com.mbarek0.web.huntersleague.model.enums.SpeciesType;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.time.LocalDateTime;
import java.util.UUID;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CompetitionRequestVM {


        @NotBlank(message = "Location is required.")
        private String location;

        @NotNull(message = "Date is required.")
        private LocalDateTime date;

        @NotNull(message = "Species type is required.")
        private SpeciesType speciesType;

        @NotNull(message = "Minimum participants is required.")
        private Integer minParticipants;

        @NotNull(message = "Maximum participants is required.")
        private Integer maxParticipants;

}
