package com.mbarek0.web.huntersleague.web.vm.mapper;


import com.mbarek0.web.huntersleague.model.Competition;
import com.mbarek0.web.huntersleague.web.vm.response.CompetitionVM;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface CompetitionVMMapper {
    CompetitionVMMapper INSTANCE = Mappers.getMapper(CompetitionVMMapper.class);

    CompetitionVM toCompetitionVM(Competition competition);
    Competition competitionVMToCompetition(CompetitionVM competitionVM);
}
