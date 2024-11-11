package com.mbarek0.web.huntersleague.web.vm.response;

import com.mbarek0.web.huntersleague.model.User;
import com.mbarek0.web.huntersleague.model.Competition;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
@Builder
public class ParticipationResponseVM {
    private UUID id;
    private UserResponseVM user;
    private CompetitionResponseVM competition;
    private Double score;
}
