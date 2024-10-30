package com.mbarek0.web.huntersleague.web.vm.mapper;

import com.mbarek0.web.huntersleague.model.User;
import com.mbarek0.web.huntersleague.web.vm.request.RegisterVM;
import com.mbarek0.web.huntersleague.web.vm.response.UserVM;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface UserVMMapper {

    UserVMMapper INSTANCE = Mappers.getMapper(UserVMMapper.class);

    UserVM toUserVM(User user);
    User toUser(RegisterVM registerVM);
    User registerVMToUser(RegisterVM registerVM);
    User userVMToUser(UserVM userVM);
}
