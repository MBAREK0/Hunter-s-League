package com.mbarek0.web.huntersleague.web.vm.response;

import com.mbarek0.web.huntersleague.model.enums.SpeciesType;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.UUID;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class CompetitionVM {

        private UUID id;

        @NotBlank(message = "Code is required.")
        private String code;

        @NotBlank(message = "Location is required.")
        private String location;

        private LocalDateTime date;

        private String speciesType;

        private Integer minParticipants;

        private Integer maxParticipants;

        private Boolean openRegistration;

}
