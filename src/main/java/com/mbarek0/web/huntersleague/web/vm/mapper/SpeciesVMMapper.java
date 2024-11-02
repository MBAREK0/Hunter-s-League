package com.mbarek0.web.huntersleague.web.vm.mapper;

import com.mbarek0.web.huntersleague.model.Species;
import com.mbarek0.web.huntersleague.web.vm.request.SpeciesVM;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface SpeciesVMMapper {

    SpeciesVM toSpeciesVM(Species species);
    Species toSpecies(SpeciesVM speciesVM);
    Species speciesVMToSpecies(SpeciesVM speciesVM);
}
