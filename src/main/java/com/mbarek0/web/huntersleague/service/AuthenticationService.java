package com.mbarek0.web.huntersleague.service;

import com.mbarek0.web.huntersleague.model.enums.Role;
import com.mbarek0.web.huntersleague.web.exception.user.UserNameAlreadyExistsException;
import com.mbarek0.web.huntersleague.web.exception.user.UsernameOrPasswordInvalidException;
import com.mbarek0.web.huntersleague.web.vm.mapper.UserVMMapper;
import com.mbarek0.web.huntersleague.web.vm.response.TokenVM;
import com.mbarek0.web.huntersleague.web.vm.request.RegisterVM;
import com.mbarek0.web.huntersleague.model.User;
import com.mbarek0.web.huntersleague.repository.UserRepository;
import com.mbarek0.web.huntersleague.util.PasswordUtil;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class AuthenticationService {
    private final UserRepository userRepository;
    private final JwtService jwtService;
    private final UserVMMapper userVMMapper;
    private final UserService userService;
    private final LocalDateTime licenseExpirationDate = LocalDateTime.now().plusYears(1);



    public TokenVM register(@Valid RegisterVM registerVM) {

        userService.findByUsername(registerVM.getUsername())
                .ifPresent(value -> {
                    throw new UserNameAlreadyExistsException("User already exists");
                });

        User newUser = userVMMapper.toUser(registerVM);

        newUser.setPassword(PasswordUtil.hashPassword(newUser.getPassword()));
        newUser.setJoinDate(LocalDateTime.now());
        newUser.setLicenseExpirationDate(licenseExpirationDate);
        newUser.setRole(Role.MEMBER);

        User user =  userRepository.save(newUser);
        String authToken = jwtService.generateToken(user.getUsername());

        return TokenVM.builder().token(authToken).build();

    }

    public TokenVM login(String username, String password) {
        return userRepository.findByUsername(username)
                .filter(user -> PasswordUtil.checkPassword(password, user.getPassword()))
                .map(user -> {
                    String authToken = jwtService.generateToken(user.getUsername());
                    return TokenVM.builder()
                            .token(authToken)
                            .build();
                })
                .orElseThrow(() -> new UsernameOrPasswordInvalidException("Invalid credentials."));
    }
}
