package com.mbarek0.web.huntersleague.web.rest.controller;

import com.mbarek0.web.huntersleague.model.Participation;
import com.mbarek0.web.huntersleague.service.ParticipationService;
import com.mbarek0.web.huntersleague.service.dto.ParticipationResultDTO;
import com.mbarek0.web.huntersleague.service.dto.PodiumDTO;
import com.mbarek0.web.huntersleague.web.vm.mapper.ParticipationVMMapper;
import com.mbarek0.web.huntersleague.web.vm.request.ParticipationRequestVM;
import com.mbarek0.web.huntersleague.web.vm.response.ParticipationResponseVM;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@PreAuthorize("hasRole('MEMBER')")
@RequestMapping("/api/v1/participation")
@RequiredArgsConstructor
public class ParticipationController {

    private final ParticipationService participationService;
    private final ParticipationVMMapper participationVMMapper;


    @PostMapping
    public ResponseEntity<ParticipationResponseVM> createParticipation(@Valid @RequestBody ParticipationRequestVM participation) {
        Participation createdParticipation = participationService.createParticipation(participation.getUser(), participation.getCompetition());
        return ResponseEntity.status(HttpStatus.CREATED).body(participationVMMapper.toParticipationResponseVM(createdParticipation));
    }

    @GetMapping("/results")
    public ResponseEntity<Page<ParticipationResultDTO>> getUserAllCompetitionsResults(@RequestParam UUID userId,
                                                                                      @RequestParam(defaultValue = "0") int page,
                                                                                      @RequestParam(defaultValue = "10") int size) {

        Page<ParticipationResultDTO> results = participationService.getUserResults(userId, page, size);
        return ResponseEntity.ok(results);
    }

    @GetMapping("/competition-result")
    public ResponseEntity<ParticipationResultDTO> getUserSingleCompetitionResult(
            @RequestParam UUID userId, @RequestParam UUID competitionId) {

        ParticipationResultDTO result = participationService.getUserCompetitionResult(userId, competitionId);
        return ResponseEntity.ok(result);
    }

    @GetMapping("/podium")
    public ResponseEntity<List<PodiumDTO>> getTopThreeParticipants(@RequestParam UUID competitionId){
        List<PodiumDTO> podium = participationService.getTopThreeParticipants(competitionId);
        return ResponseEntity.ok(podium);
    }

}
