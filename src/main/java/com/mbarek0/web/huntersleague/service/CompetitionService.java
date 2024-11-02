package com.mbarek0.web.huntersleague.service;

import com.mbarek0.web.huntersleague.model.Competition;
import com.mbarek0.web.huntersleague.model.User;
import com.mbarek0.web.huntersleague.repository.CompetitionRepository;
import com.mbarek0.web.huntersleague.web.exception.competition.CompetitionAlreadyExistsException;
import com.mbarek0.web.huntersleague.web.exception.competition.CompetitionNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class CompetitionService {

    private final CompetitionRepository competitionRepository;

    public Page<Competition> getAllCompetitions(int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        return competitionRepository.findAll(pageable);
    }

    public Page<Competition> searchByCodeOrLocationOrDate(String searchKeyword, int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        return competitionRepository.findByCodeContainingOrLocationContaining(searchKeyword, searchKeyword, pageable);
    }

    public Competition getCompetitionById(UUID id) {
        return competitionRepository.findById(id)
                .orElseThrow(() -> new CompetitionNotFoundException("Competition not found with " ));
    }

    public Competition createCompetition(Competition competition) {

        getCompetitionByCode(competition.getCode())
                .ifPresent(c -> {
                    throw new CompetitionAlreadyExistsException("Competition with code " + c.getCode() + " already exists");
                });

        getCompetitionByLocationAndDate(competition.getLocation(), competition.getDate())
                .ifPresent(c -> {
                    throw new CompetitionAlreadyExistsException("Competition with location " + c.getLocation() + " and date " + c.getDate() + " already exists");
                });

        return competitionRepository.save(competition);
    }


    public Competition updateCompetition(Competition competitionDetails) {

        Competition competition = getCompetitionById(competitionDetails.getId());
        competition.setCode(competitionDetails.getCode());
        competition.setLocation(competitionDetails.getLocation());
        competition.setDate(competitionDetails.getDate());
        competition.setSpeciesType(competitionDetails.getSpeciesType());
        competition.setMinParticipants(competitionDetails.getMinParticipants());
        competition.setMaxParticipants(competitionDetails.getMaxParticipants());
        competition.setOpenRegistration(competitionDetails.getOpenRegistration());

        getCompetitionByCode(competition.getCode())
                .ifPresent(c -> {
                    throw new CompetitionAlreadyExistsException("Competition with code " + c.getCode() + " already exists");
                });

        getCompetitionByLocationAndDate(competition.getLocation(), competition.getDate())
                .ifPresent(c -> {
                    throw new CompetitionAlreadyExistsException("Competition with location " + c.getLocation() + " and date " + c.getDate() + " already exists");
                });
        
        return competitionRepository.save(competition);
    }

    public void deleteCompetition(UUID id) {
        // delete all related data ...................
        //
        Competition competition = getCompetitionById(id);
        competitionRepository.delete(competition);
    }


    public Optional<Competition> getCompetitionByCode(String code) {
        return competitionRepository.findByCode(code);
    }

    public Optional<Competition> getCompetitionByLocationAndDate(String location, LocalDateTime date) {
        return competitionRepository.findByLocationAndDate(location, date);
    }
}
