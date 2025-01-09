package com.mbarek0.web.huntersleague.service;

import com.mbarek0.web.huntersleague.model.Competition;
import com.mbarek0.web.huntersleague.repository.CompetitionRepository;
import com.mbarek0.web.huntersleague.repository.dto.CompetitionRepoDTO;
import com.mbarek0.web.huntersleague.web.exception.FieldCannotBeNullException;
import com.mbarek0.web.huntersleague.web.exception.competition.CompetitionAlreadyExistsException;
import com.mbarek0.web.huntersleague.web.exception.competition.CompetitionNotFoundException;
import com.mbarek0.web.huntersleague.web.exception.competition.OnlyOneCompetitionCanBeScheduledPerWeekException;
import com.mbarek0.web.huntersleague.web.exception.participation.ParticipantLimitsException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Objects;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class CompetitionService {

    private final CompetitionRepository competitionRepository;
    private final JobProcessorService jobProcessorService;

    public Page<Competition> getAllCompetitions(int page, int size, String sortField) {
        String sortDirection = "ASC";
        if (Objects.equals(sortField.toLowerCase(), "date".toLowerCase())) sortDirection = "DESC";

        Sort sort = Sort.by(Sort.Direction.fromString(sortDirection), sortField);
        Pageable pageable = PageRequest.of(page, size, sort);

        return competitionRepository.findAll(pageable);
    }

    public Page<Competition> searchByCodeOrLocationOrDate(String searchKeyword, int page, int size, String sortField) {
        String sortDirection = "ASC";
        if (Objects.equals(sortField.toLowerCase(), "date".toLowerCase())) sortDirection = "DESC";

        Sort sort = Sort.by(Sort.Direction.fromString(sortDirection), sortField);
        Pageable pageable = PageRequest.of(page, size, sort);

        return competitionRepository.findByCodeContainingOrLocationContainingAndDeletedFalse(searchKeyword, searchKeyword, pageable);
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
        if (!competition.getDate().equals(competitionDetails.getDate()))
            validateUpdatedCompetitionDate(competition.getDate(),competitionDetails.getDate());
        String code = generateCompetitionCode(competition.getLocation(), competition.getDate());
        competition.setCode(code);
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
        competitionRepository.softDeleteById(id);
    }


    public Optional<Competition> getCompetitionByCode(String code) {
        return competitionRepository.findByCodeAndDeletedFalse(code);
    }

    public Optional<Competition> getCompetitionByLocationAndDate(String location, LocalDateTime date) {
        return competitionRepository.findByLocationAndDateAndDeletedFalse(location, date);
    }

    private void validateCompetitionDate(LocalDateTime competitionDate) {
        boolean competitionExists = competitionRepository.existsByDateBetweenAndDeletedFalse(
                competitionDate.minusDays(7), competitionDate.plusDays(7)
        );

        if (competitionExists) {
            throw new OnlyOneCompetitionCanBeScheduledPerWeekException("Only one competition can be scheduled per week.");
        }
    }

    private void validateUpdatedCompetitionDate(LocalDateTime oldCompetitionDate, LocalDateTime newCompetitionDate) {
    // create interval of 7 days befor and after old date
    // if the new date is in the interval check if the new data 7 days befor and after have just one competition( the old one)
    // if not throw exception
        LocalDateTime startInterval = oldCompetitionDate.minusDays(7);
        LocalDateTime endInterval = oldCompetitionDate.plusDays(7);
        if(newCompetitionDate.isAfter(startInterval) && newCompetitionDate.isBefore(endInterval)){
              LocalDateTime startIntervalNew = newCompetitionDate.minusDays(7);
              LocalDateTime endIntervalNew = newCompetitionDate.plusDays(7);
              int totalCompetitionExists = competitionRepository.countByDateBetweenAndDeletedFalse(
                      startIntervalNew, endIntervalNew
              );
            if(totalCompetitionExists > 1) throw new OnlyOneCompetitionCanBeScheduledPerWeekException("Only one competition can be scheduled per week.");
            else return;
        }else this.validateCompetitionDate(newCompetitionDate);
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

    public CompetitionRepoDTO getCompetitionDetailsById(UUID id) {
        return competitionRepository.findByIdRepoDTO(id);
    }

    @Transactional
    public void markCompetitionAsDeleted(UUID competitionId) {
        // Mark the competition as deleted
        competitionRepository.findById(competitionId).ifPresent(competition -> {
            competition.setDeleted(true);
            competitionRepository.save(competition);

            // Create a job for cascading delete
            jobProcessorService.createCascadeDeleteJob(competitionId, "COMPETITION");
        });
    }
}
