package com.mbarek0.web.huntersleague.service;

import com.mbarek0.web.huntersleague.model.Hunt;
import com.mbarek0.web.huntersleague.model.Participation;
import com.mbarek0.web.huntersleague.model.Species;
import com.mbarek0.web.huntersleague.model.User;
import com.mbarek0.web.huntersleague.model.enums.SpeciesType;
import com.mbarek0.web.huntersleague.repository.HuntRepository;
import com.mbarek0.web.huntersleague.web.exception.competition.HuntWeightException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.UUID;

@RequiredArgsConstructor
@Service
public class HuntService {

    private final HuntRepository huntRepository;
    private final ParticipationService participationService;
    private final SpeciesService speciesService;
    private final UserService userService;

    @Transactional
    public Hunt saveHunt(UUID participationId, UUID speciesId, double weight) {

        Species species = speciesService.getSpeciesById(speciesId);
        Participation participation = participationService.getParticipationById(participationId);


        if (weight < species.getMinimumWeight())
                throw new HuntWeightException("Hunt weight is less than the minimum weight of the species");

        Hunt hunt = Hunt.builder()
                .species(species)
                .weight(weight)
                .participation(participation)
                .build();


        // calculate the score of the hunt
        double speciesFactor = species.getCategory().getValue();
        double difficultyFactor = species.getDifficulty().getValue();
        double score = species.getPoints() + (hunt.getWeight() * speciesFactor) * difficultyFactor;

        // update the score for the participation
        participation.setScore(participation.getScore() + score);
        participationService.updateScore(participation);

        hunt.setParticipation(participation);
        hunt.setSpecies(species);
        Hunt savedHunt = huntRepository.save(hunt);

        return savedHunt;
    }


}
