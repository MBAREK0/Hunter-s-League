package com.mbarek0.web.huntersleague.web.rest.controller;

import com.mbarek0.web.huntersleague.model.Species;
import com.mbarek0.web.huntersleague.model.enums.Role;
import com.mbarek0.web.huntersleague.model.enums.SpeciesType;
import com.mbarek0.web.huntersleague.service.SpeciesService;
import com.mbarek0.web.huntersleague.util.Helper;
import com.mbarek0.web.huntersleague.web.vm.mapper.SpeciesVMMapper;
import com.mbarek0.web.huntersleague.web.vm.request.SpeciesRequestVM;
import com.mbarek0.web.huntersleague.web.vm.response.SpeciesResponseVM;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/species")
@RequiredArgsConstructor
public class SpeciesController {
    private final SpeciesService speciesService;
    private final SpeciesVMMapper speciesVMMapper;

    @GetMapping
    public ResponseEntity<List<SpeciesResponseVM>> getAllSpecies(@RequestParam(required = false) SpeciesType category,
                                                                    @RequestParam(defaultValue = "0") int page,
                                                                    @RequestParam(defaultValue = "10") int size) {
        Page<Species> speciesPage = (category != null)
                ? speciesService.getSpeciesByCategory(category, page, size)
                : speciesService.getAllSpecies(page, size);

        List<SpeciesResponseVM> speciesVMs = speciesPage.stream()
                .map(speciesVMMapper::toSpeciesResponseVM)
                .toList();
        return ResponseEntity.ok(speciesVMs);
    }


    @GetMapping("/{id}")
    public ResponseEntity<SpeciesResponseVM> getSpeciesById(@PathVariable UUID id) {
        Species species = speciesService.getSpeciesById(id);
        return ResponseEntity.ok(speciesVMMapper.toSpeciesResponseVM(species));
    }


    @PostMapping
    public ResponseEntity<SpeciesResponseVM> createSpecies( @Valid @RequestBody SpeciesRequestVM speciesVM) {

        Species species = speciesVMMapper.SpeciesRequestVMTOSpecies(speciesVM);
        Species createdSpecies = speciesService.createSpecies(species);
        SpeciesResponseVM speciesResponseVM = speciesVMMapper.toSpeciesResponseVM(createdSpecies);

        return ResponseEntity.status(HttpStatus.CREATED).body(speciesResponseVM);
    }

    @PutMapping("/{id}")
    public ResponseEntity<SpeciesResponseVM> updateSpecies(@PathVariable UUID id,
                                                           @Valid @RequestBody SpeciesRequestVM speciesVM) {
        Species updatedSpecies = speciesService.updateSpecies(id, speciesVMMapper.SpeciesRequestVMTOSpecies(speciesVM));
        return ResponseEntity.ok(speciesVMMapper.toSpeciesResponseVM(updatedSpecies));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteSpecies(@PathVariable UUID id) {
        speciesService.deleteSpecies(id);
        return ResponseEntity.noContent().build();
    }
}
