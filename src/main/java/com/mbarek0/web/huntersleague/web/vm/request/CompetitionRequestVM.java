package com.mbarek0.web.huntersleague.web.vm.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
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
public class CompetitionRequestVM {

        @NotBlank(message = "Code is required.")
        private String code;

        @NotBlank(message = "Location is required.")
        private String location;

        @NotNull(message = "Date is required.")
        private LocalDateTime date;

        private String speciesType;

        @NotNull(message = "Minimum participants is required.")
        private Integer minParticipants;

        @NotNull(message = "Maximum participants is required.")
        private Integer maxParticipants;

}
