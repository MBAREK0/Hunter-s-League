package com.mbarek0.web.huntersleague.web.rest.controller;


import com.mbarek0.web.huntersleague.model.Competition;
import com.mbarek0.web.huntersleague.model.enums.Role;
import com.mbarek0.web.huntersleague.repository.dto.CompetitionRepoDTO;
import com.mbarek0.web.huntersleague.service.CompetitionService;
import com.mbarek0.web.huntersleague.util.Helper;
import com.mbarek0.web.huntersleague.web.vm.mapper.CompetitionVMMapper;
import com.mbarek0.web.huntersleague.web.vm.request.CompetitionRequestVM;
import com.mbarek0.web.huntersleague.web.vm.response.CompetitionResponseVM;
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
@RequestMapping("/api/v1/competition")
@RequiredArgsConstructor
public class CompetitionController {

    private final CompetitionService competitionService;
    private final CompetitionVMMapper CompetitionVMMapper;

    @GetMapping
    public ResponseEntity<List<CompetitionResponseVM>> getAllCompetitions(@RequestParam(required = false) String searchKeyword,
                                                                          @RequestParam(defaultValue = "0") int page,
                                                                          @RequestParam(defaultValue = "10") int size) {
        Page<Competition> competitions= (searchKeyword == null)
                ? competitionService.getAllCompetitions(page, size)
                : competitionService.searchByCodeOrLocationOrDate(searchKeyword, page, size);

        List<CompetitionResponseVM> competitionVMs = competitions.stream()
                .map(CompetitionVMMapper::toCompetitionResponseVM)
                .toList();
        return ResponseEntity.ok(competitionVMs);
    }

    @GetMapping("/{id}")
    public ResponseEntity<CompetitionResponseVM> getCompetitionById(@PathVariable UUID id) {
        Competition competition = competitionService.getCompetitionById(id);
        return ResponseEntity.ok(CompetitionVMMapper.toCompetitionResponseVM(competition));
    }
    @GetMapping("/details/{id}")
    public ResponseEntity<CompetitionRepoDTO> getCompetitionDetailsById(@PathVariable UUID id) {
        CompetitionRepoDTO competition = competitionService.getCompetitionDetailsById(id);
        return ResponseEntity.ok(competition);
    }



    @PostMapping
    public ResponseEntity<CompetitionResponseVM> createCompetition(HttpServletRequest request, @Valid @RequestBody CompetitionRequestVM competition) {
        if (!Helper.isAuthorized(request, Role.ADMIN)) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }
        Competition createdCompetition = competitionService.createCompetition(CompetitionVMMapper.CompetitionRequestVMToCompetition(competition));
        return ResponseEntity.status(HttpStatus.CREATED).body(CompetitionVMMapper.toCompetitionResponseVM(createdCompetition));
    }

    @PutMapping("/{id}")
    public ResponseEntity<CompetitionResponseVM> updateCompetition(@PathVariable UUID id,
                                                                    HttpServletRequest request,
                                                                  @Valid @RequestBody CompetitionRequestVM competitionDetails) {
        if (!Helper.isAuthorized(request, Role.ADMIN)) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }

        Competition competition = CompetitionVMMapper.CompetitionRequestVMToCompetition(competitionDetails);
        Competition updatedCompetition = competitionService.updateCompetition(competition,id);
        return ResponseEntity.ok(CompetitionVMMapper.toCompetitionResponseVM(updatedCompetition));

    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCompetition(@PathVariable UUID id) {
        competitionService.markCompetitionAsDeleted(id);
        return ResponseEntity.noContent().build();
    }
}