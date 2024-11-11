package com.mbarek0.web.huntersleague.web.vm.mapper;

import com.mbarek0.web.huntersleague.model.Participation;
import com.mbarek0.web.huntersleague.web.vm.response.ParticipationResponseVM;
import org.mapstruct.Mapper;;

@Mapper(componentModel = "spring", uses = {UserVMMapper.class, CompetitionVMMapper.class})
public interface ParticipationVMMapper {
     ParticipationResponseVM toParticipationResponseVM(Participation participation);
}
