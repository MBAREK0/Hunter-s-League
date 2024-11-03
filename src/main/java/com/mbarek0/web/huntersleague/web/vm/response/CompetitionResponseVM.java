package com.mbarek0.web.huntersleague.web.vm.response;

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
public class CompetitionResponseVM {

        private UUID id;

        private String code;

        private String location;

        private LocalDateTime date;

        private String speciesType;

        private Integer minParticipants;

        private Integer maxParticipants;

        private Boolean openRegistration;
}
