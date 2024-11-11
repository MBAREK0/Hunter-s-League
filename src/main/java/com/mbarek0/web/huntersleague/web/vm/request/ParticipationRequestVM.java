package com.mbarek0.web.huntersleague.web.vm.request;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
public class ParticipationRequestVM {

    public ParticipationRequestVM() {
    }

    @NotNull(message = "User is required")
    private UUID user;

    @NotNull(message = "Competition is required")
    private UUID competition;
}
