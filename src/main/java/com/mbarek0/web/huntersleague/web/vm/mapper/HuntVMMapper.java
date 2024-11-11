package com.mbarek0.web.huntersleague.web.vm.mapper;

import com.mbarek0.web.huntersleague.model.Hunt;
import com.mbarek0.web.huntersleague.web.vm.request.HuntRequestVM;
import com.mbarek0.web.huntersleague.web.vm.response.HuntResponseVM;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring", uses = {SpeciesVMMapper.class, ParticipationVMMapper.class})
public interface HuntVMMapper {
    HuntResponseVM toHuntResponseVM(Hunt hunt);
}
