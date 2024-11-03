package com.mbarek0.web.huntersleague.service;

import com.mbarek0.web.huntersleague.model.Competition;
import com.mbarek0.web.huntersleague.model.User;
import com.mbarek0.web.huntersleague.repository.CompetitionRepository;
import com.mbarek0.web.huntersleague.web.exception.FieldCannotBeNullException;
import com.mbarek0.web.huntersleague.web.exception.competition.CompetitionAlreadyExistsException;
import com.mbarek0.web.huntersleague.web.exception.competition.CompetitionNotFoundException;
import com.mbarek0.web.huntersleague.web.exception.competition.OnlyOneCompetitionCanBeScheduledPerWeekException;
import com.mbarek0.web.huntersleague.web.exception.competition.ParticipantLimitsException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
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

        if (competition.getMinParticipants() == null) throw   new FieldCannotBeNullException("Minimum participants cannot be null");
        if (competition.getMaxParticipants() == null) throw   new FieldCannotBeNullException("Maximum participants cannot be null");

        validateParticipantLimits(competition.getMinParticipants(), competition.getMaxParticipants());
        validateCompetitionDate(competition.getDate());
        getCompetitionByCode(competition.getCode())
                .ifPresent(c -> {
                    throw new CompetitionAlreadyExistsException("Competition with code " + c.getCode() + " already exists");
                });

        getCompetitionByLocationAndDate(competition.getLocation(), competition.getDate())
                .ifPresent(c -> {
                    throw new CompetitionAlreadyExistsException("Competition with location " + c.getLocation() + " and date " + c.getDate() + " already exists");
                });

        String code = generateCompetitionCode(competition.getLocation(), competition.getDate());
        competition.setCode(code);
        competition.setOpenRegistration(true);
        return competitionRepository.save(competition);
    }

    public Competition updateCompetition(Competition competitionDetails, UUID id) {

        if (competitionDetails.getSpeciesType() == null) throw   new FieldCannotBeNullException("Species type cannot be null");

        Competition competition = getCompetitionById(id);
        competition.setCode(competitionDetails.getCode());
        competition.setLocation(competitionDetails.getLocation());
        competition.setDate(competitionDetails.getDate());
        competition.setSpeciesType(competitionDetails.getSpeciesType());
        competition.setMinParticipants(competitionDetails.getMinParticipants());
        competition.setMaxParticipants(competitionDetails.getMaxParticipants());
        competition.setOpenRegistration(competitionDetails.getOpenRegistration());

        validateParticipantLimits(competition.getMinParticipants(), competition.getMaxParticipants());
        validateCompetitionDate(competition.getDate());

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

    private void validateCompetitionDate(LocalDateTime competitionDate) {
        boolean competitionExists = competitionRepository.existsByDateBetween(
                competitionDate.minusDays(7), competitionDate.plusDays(7)
        );

        if (competitionExists) {
            throw new OnlyOneCompetitionCanBeScheduledPerWeekException("Only one competition can be scheduled per week.");
        }
    }

    private void validateParticipantLimits(int minParticipants, int maxParticipants) {
        if (minParticipants >= maxParticipants) {
            throw new ParticipantLimitsException("Minimum participants must be less than maximum participants.");
        }
    }

    private String generateCompetitionCode(String location, LocalDateTime date) {
        String locationCode = location.toUpperCase();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        String dateCode = date.format(formatter);
        return locationCode + '-' + dateCode;
    }

}
