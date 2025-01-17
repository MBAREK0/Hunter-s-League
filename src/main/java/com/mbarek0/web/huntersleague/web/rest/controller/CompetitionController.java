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
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/competitions")
@RequiredArgsConstructor
@Validated
public class CompetitionController {

    private final CompetitionService competitionService;
    private final CompetitionVMMapper CompetitionVMMapper;

    @GetMapping
    @PreAuthorize("hasAuthority('CAN_VIEW_COMPETITIONS')")
    public ResponseEntity<Page<CompetitionResponseVM>> getAllCompetitions(@RequestParam(required = false) String searchKeyword,
                                                                          @RequestParam(defaultValue = "0") int page,
                                                                          @RequestParam(defaultValue = "10") int size,
                                                                          @RequestParam(required = false) String sortField) {
        Page<Competition> competitions = (searchKeyword == null || searchKeyword.isEmpty())
                ? competitionService.getAllCompetitions(page, size, sortField)
                : competitionService.searchByCodeOrLocationOrDate(searchKeyword, page, size, sortField);

        Page<CompetitionResponseVM> competitionVMs = competitions.map(CompetitionVMMapper::toCompetitionResponseVM);
        return ResponseEntity.ok(competitionVMs);
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAuthority('CAN_VIEW_COMPETITIONS')")
    public ResponseEntity<CompetitionResponseVM> getCompetitionById(@PathVariable UUID id) {
        Competition competition = competitionService.getCompetitionById(id);
        return ResponseEntity.ok(CompetitionVMMapper.toCompetitionResponseVM(competition));
    }

    @GetMapping("/details/{id}")
    @PreAuthorize("hasAuthority('CAN_VIEW_COMPETITIONS')")
    public ResponseEntity<CompetitionRepoDTO> getCompetitionDetailsById(@PathVariable UUID id) {
        CompetitionRepoDTO competition = competitionService.getCompetitionDetailsById(id);
        return ResponseEntity.ok(competition);

    }

    @PostMapping
    //CAN_MANAGE_COMPETITIONS
    @PreAuthorize("hasAuthority('CAN_MANAGE_COMPETITIONS')")
    public ResponseEntity<CompetitionResponseVM> createCompetition(@Valid @RequestBody CompetitionRequestVM competition) {
        Competition createdCompetition = competitionService.createCompetition(CompetitionVMMapper.CompetitionRequestVMToCompetition(competition));
        return ResponseEntity.status(HttpStatus.CREATED).body(CompetitionVMMapper.toCompetitionResponseVM(createdCompetition));
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasAuthority('CAN_MANAGE_COMPETITIONS')")
    public ResponseEntity<CompetitionResponseVM> updateCompetition(@PathVariable UUID id,
                                                                  @Valid @RequestBody CompetitionRequestVM competitionDetails) {

        Competition competition = CompetitionVMMapper.CompetitionRequestVMToCompetition(competitionDetails);
        Competition updatedCompetition = competitionService.updateCompetition(competition,id);
        return ResponseEntity.ok(CompetitionVMMapper.toCompetitionResponseVM(updatedCompetition));

    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAuthority('CAN_MANAGE_COMPETITIONS')")
    public ResponseEntity<Void> deleteCompetition(@PathVariable UUID id) {
        competitionService.markCompetitionAsDeleted(id);
        return ResponseEntity.noContent().build();
    }
}