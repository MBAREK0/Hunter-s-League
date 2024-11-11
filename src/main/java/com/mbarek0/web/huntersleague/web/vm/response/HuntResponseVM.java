package com.mbarek0.web.huntersleague.web.vm.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.UUID;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class HuntResponseVM {

    private UUID id;
    private SpeciesResponseVM species;
    private Double weight;
    private ParticipationResponseVM participation;

}
