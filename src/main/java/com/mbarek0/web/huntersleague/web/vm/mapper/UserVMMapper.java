package com.mbarek0.web.huntersleague.web.vm.mapper;

import com.mbarek0.web.huntersleague.model.User;
import com.mbarek0.web.huntersleague.web.vm.request.RegisterVM;
import com.mbarek0.web.huntersleague.web.vm.request.UserRequestVM;
import com.mbarek0.web.huntersleague.web.vm.response.UserResponseVM;
import jakarta.validation.Valid;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UserVMMapper {

    User userRequestVMtoUser(UserRequestVM userVm);
    UserResponseVM userToUserResponseVM(User createdUser);
    User registerVMtoUser(RegisterVM registerVM);

}
