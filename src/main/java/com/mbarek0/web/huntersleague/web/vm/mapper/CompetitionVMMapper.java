package com.mbarek0.web.huntersleague.web.vm.mapper;


import com.mbarek0.web.huntersleague.model.Competition;
import com.mbarek0.web.huntersleague.web.vm.request.CompetitionRequestVM;
import com.mbarek0.web.huntersleague.web.vm.response.CompetitionResponseVM;
import jakarta.validation.Valid;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import org.springframework.stereotype.Component;

@Mapper(componentModel = "spring")
public interface CompetitionVMMapper {

     CompetitionResponseVM toCompetitionResponseVM(Competition competition);

    Competition CompetitionRequestVMToCompetition(CompetitionRequestVM competition);
}
