package com.mbarek0.web.huntersleague.web.rest.controller;

import com.mbarek0.web.huntersleague.model.Participation;
import com.mbarek0.web.huntersleague.service.ParticipationService;
import com.mbarek0.web.huntersleague.web.vm.mapper.ParticipationVMMapper;
import com.mbarek0.web.huntersleague.web.vm.request.ParticipationRequestVM;
import com.mbarek0.web.huntersleague.web.vm.response.ParticipationResponseVM;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
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



}
