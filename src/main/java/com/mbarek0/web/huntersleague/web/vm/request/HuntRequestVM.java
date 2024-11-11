package com.mbarek0.web.huntersleague.web.vm.request;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
public class HuntRequestVM {

    @NotNull(message = "Species ID is required")
    private UUID speciesId;

    @NotNull(message = "Weight is required")
    private Double weight;

    @NotNull(message = "Participation ID is required")
    private UUID participationId;
}
