package com.mbarek0.web.huntersleague.web.vm.mapper;

import com.mbarek0.web.huntersleague.model.Species;
import com.mbarek0.web.huntersleague.web.vm.request.SpeciesRequestVM;
import com.mbarek0.web.huntersleague.web.vm.response.SpeciesResponseVM;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface SpeciesVMMapper {

    SpeciesResponseVM toSpeciesResponseVM(Species species);
    Species SpeciesRequestVMTOSpecies(SpeciesRequestVM speciesRequestVM);

}
