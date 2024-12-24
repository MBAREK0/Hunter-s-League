package com.mbarek0.web.huntersleague.service;

import com.mbarek0.web.huntersleague.model.Competition;
import com.mbarek0.web.huntersleague.model.Participation;
import com.mbarek0.web.huntersleague.model.User;
import com.mbarek0.web.huntersleague.repository.CompetitionRepository;
import com.mbarek0.web.huntersleague.repository.ParticipationRepository;
import com.mbarek0.web.huntersleague.repository.UserRepository;
import com.mbarek0.web.huntersleague.service.dto.PodiumDTO;
import com.mbarek0.web.huntersleague.service.dto.ParticipationResultDTO;
import com.mbarek0.web.huntersleague.web.exception.FieldCannotBeNullException;
import com.mbarek0.web.huntersleague.web.exception.competition.CompetitionIsNotOpenForRegistrationException;
import com.mbarek0.web.huntersleague.web.exception.competition.CompetitionNotFoundException;
import com.mbarek0.web.huntersleague.web.exception.participation.ParticipationNotFoundException;
import com.mbarek0.web.huntersleague.web.exception.user.UserNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;


@Service
@RequiredArgsConstructor
public class ParticipationService {

    private final ParticipationRepository participationRepository;
    private final UserRepository userRepository;
    private final CompetitionRepository competitionRepository;
    private final CompetitionService competitionService;

    public Participation createParticipation(UUID userId, UUID competitionId) {

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException("User not found with " ));
        Competition competition =  competitionRepository.findById(competitionId)
                .orElseThrow(() -> new CompetitionNotFoundException("Competition not found with " ));

        if (!competition.getOpenRegistration())
            throw new CompetitionIsNotOpenForRegistrationException("Registration is closed for this competition");

        if (participationRepository.existsByUserAndCompetition(user, competition))
              throw new CompetitionNotFoundException("User already registered for this competition");

       LocalDateTime now = LocalDateTime.now();
       if (user.getLicenseExpirationDate().isBefore(now))
           throw new UserNotFoundException("User license is expired");

        Participation participation = new Participation(competition, user, 0.0);

        return participationRepository.save(participation);
    }

    public void updateScore(Participation participation) {
        participationRepository.save(participation);
    }

    public Participation getParticipationById(UUID participationId) {
        return participationRepository.findById(participationId)
                .orElseThrow(() -> new CompetitionNotFoundException("Participation not found with " ));
    }

    // ------------------------------- Result -------------------------------
    public Page<ParticipationResultDTO> getUserResults(UUID userId, int page, int size) {
        if (userId ==null){
            throw new FieldCannotBeNullException("userId cant be null");
        }
        Pageable pageable = PageRequest.of(page, size);
        Page<Participation> participations = participationRepository.findByUserIdAndDeletedFalse(userId, pageable);

        return participations.map(participation -> ParticipationResultDTO.builder()
                .competitionCode(participation.getCompetition() != null ? participation.getCompetition().getCode() : null)
                .speciesType(participation.getHunts() != null && !participation.getHunts().isEmpty()
                        ? participation.getHunts().get(0).getSpecies().getCategory()
                        : null)
                .score(participation.getScore())
                .build());
    }


    public ParticipationResultDTO getUserCompetitionResult(UUID userId, UUID competitionId) {
        Participation participation = participationRepository.findByUserIdAndCompetitionId(userId, competitionId)
                .orElseThrow(() -> new ParticipationNotFoundException("Participation not found for user and competition"));

        return ParticipationResultDTO.builder()
                .competitionCode(participation.getCompetition().getCode())
                .speciesType(participation.getHunts().get(0).getSpecies().getCategory())
                .score(participation.getScore())
                .build();
    }

    public List<PodiumDTO> getTopThreeParticipants(UUID competitionId) {
        if (competitionId == null){
            throw new FieldCannotBeNullException("competitionId cant be null");
        }
        competitionService.getCompetitionById(competitionId);
        return participationRepository.findTopThreeByCompetition(competitionId);
    }
}
