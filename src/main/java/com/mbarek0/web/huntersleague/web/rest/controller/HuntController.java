package com.mbarek0.web.huntersleague.web.rest.controller;

import com.mbarek0.web.huntersleague.model.Hunt;
import com.mbarek0.web.huntersleague.service.HuntService;
import com.mbarek0.web.huntersleague.web.vm.mapper.HuntVMMapper;
import com.mbarek0.web.huntersleague.web.vm.request.HuntRequestVM;
import com.mbarek0.web.huntersleague.web.vm.response.HuntResponseVM;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/hunts")
@RequiredArgsConstructor
public class HuntController {

    private final HuntService huntService;
    private final HuntVMMapper huntVMMapper;

    @PostMapping
    public HuntResponseVM createHunt(@RequestBody HuntRequestVM huntRequestVM) {

        Hunt savedHunt = huntService.saveHunt(huntRequestVM.getParticipationId(),huntRequestVM.getSpeciesId(),huntRequestVM.getWeight());
        return huntVMMapper.toHuntResponseVM(savedHunt);

    }
}
