package com.mbarek0.web.huntersleague.web.rest.controller;


import com.mbarek0.web.huntersleague.model.Competition;
import com.mbarek0.web.huntersleague.model.User;
import com.mbarek0.web.huntersleague.model.enums.Role;
import com.mbarek0.web.huntersleague.service.CompetitionService;
import com.mbarek0.web.huntersleague.util.Helper;
import com.mbarek0.web.huntersleague.web.vm.mapper.CompetitionVMMapper;
import com.mbarek0.web.huntersleague.web.vm.response.CompetitionVM;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/competition")
@RequiredArgsConstructor
public class CompetitionController {

    private final CompetitionService competitionService;

    @GetMapping
    public ResponseEntity<List<CompetitionVM>> getAllCompetitions( @RequestParam(required = false) String searchKeyword,
                                                                   @RequestParam(defaultValue = "0") int page,
                                                                   @RequestParam(defaultValue = "10") int size) {
        Page<Competition> competitions;
        if (searchKeyword == null)
            competitions = competitionService.getAllCompetitions(page, size);
        else
            competitions =  competitionService.searchByCodeOrLocationOrDate(searchKeyword, page, size);

        List<CompetitionVM> competitionVMs = competitions.stream()
                .map(CompetitionVMMapper.INSTANCE::toCompetitionVM)
                .toList();
        return ResponseEntity.ok(competitionVMs);
    }

    @GetMapping("/{id}")
    public ResponseEntity<CompetitionVM> getCompetitionById(@PathVariable UUID id) {
        Competition competition = competitionService.getCompetitionById(id);
        return ResponseEntity.ok(CompetitionVMMapper.INSTANCE.toCompetitionVM(competition));
    }

    @PostMapping
    public ResponseEntity<CompetitionVM> createCompetition(HttpServletRequest request,@RequestBody CompetitionVM competition) {
        if (!Helper.isAuthorized(request, Role.ADMIN)) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }
        Competition createdCompetition = competitionService.createCompetition(CompetitionVMMapper.INSTANCE.competitionVMToCompetition(competition));
        return ResponseEntity.status(HttpStatus.CREATED).body(CompetitionVMMapper.INSTANCE.toCompetitionVM(createdCompetition));
    }

    @PutMapping
    public ResponseEntity<CompetitionVM> updateCompetition(HttpServletRequest request,
                                                           @RequestBody CompetitionVM competitionDetails) {
        if (!Helper.isAuthorized(request, Role.ADMIN)) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }

        Competition competition = CompetitionVMMapper.INSTANCE.competitionVMToCompetition(competitionDetails);
        Competition updatedCompetition = competitionService.updateCompetition(competition);
        return ResponseEntity.ok(CompetitionVMMapper.INSTANCE.toCompetitionVM(updatedCompetition));

    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCompetition(@PathVariable UUID id) {
        competitionService.deleteCompetition(id);
        return ResponseEntity.noContent().build();
    }
}