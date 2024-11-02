package com.mbarek0.web.huntersleague.web.rest.controller;

import com.mbarek0.web.huntersleague.model.Species;
import com.mbarek0.web.huntersleague.service.SpeciesService;
import com.mbarek0.web.huntersleague.web.vm.mapper.SpeciesVMMapper;
import com.mbarek0.web.huntersleague.web.vm.request.SpeciesVM;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/species")
@RequiredArgsConstructor
public class SpeciesController {
     private final SpeciesService speciesService;
     private final SpeciesVMMapper speciesVMMapper;


     @PostMapping
        public void createSpecies(@Valid @RequestBody SpeciesVM speciesVM) {
            Species species = speciesVMMapper.speciesVMToSpecies(speciesVM);
            speciesService.createSpecies(species);
        }

}


