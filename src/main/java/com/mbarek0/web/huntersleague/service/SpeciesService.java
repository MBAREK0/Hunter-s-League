package com.mbarek0.web.huntersleague.service;

import com.mbarek0.web.huntersleague.model.Species;
import com.mbarek0.web.huntersleague.model.enums.SpeciesType;
import com.mbarek0.web.huntersleague.repository.SpeciesRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class SpeciesService {
    private final SpeciesRepository speciesRepository;

    public Page<Species> getAllSpecies(int page, int size) {
        return speciesRepository.findAll(Pageable.ofSize(size).withPage(page));
    }



    public Page<Species> getSpeciesByCategory(SpeciesType category, int page, int size) {
        return speciesRepository.findByCategory(category, Pageable.ofSize(size).withPage(page));
    }


    public Species getSpeciesById(UUID id) {
        return speciesRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Species not found with id: " + id));
    }

    public Species createSpecies(Species species) {
        return speciesRepository.save(species);
    }

    public Species updateSpecies(UUID id, Species speciesDetails) {
        Species existingSpecies = getSpeciesById(id);
        existingSpecies.setName(speciesDetails.getName());
        existingSpecies.setCategory(speciesDetails.getCategory());
        existingSpecies.setMinimumWeight(speciesDetails.getMinimumWeight());
        existingSpecies.setDifficulty(speciesDetails.getDifficulty());
        existingSpecies.setPoints(speciesDetails.getPoints());
        return speciesRepository.save(existingSpecies);
    }

    public void deleteSpecies(UUID id) {
        speciesRepository.deleteById(id);
    }
}
