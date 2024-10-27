package com.mbarek0.web.huntersleague.service;

import com.mbarek0.web.huntersleague.dto.AuthenticationRequest;
import com.mbarek0.web.huntersleague.dto.AuthenticationResponse;
import com.mbarek0.web.huntersleague.dto.UserDTO;
import com.mbarek0.web.huntersleague.model.User;
import com.mbarek0.web.huntersleague.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AuthenticationService {
    private final UserRepository userRepository;
    private final JwtService jwtService;


    public AuthenticationResponse register(UserDTO userDTO) {

        if (findByUsername(userDTO.getUsername()).isPresent()) {
            return AuthenticationResponse.builder()
                    .token("Username already exists.")
                    .build();
        }

        User newUser = User.builder()
                .username(userDTO.getUsername())
                .password(userDTO.getPassword())
                .firstName(userDTO.getFirstName())
                .lastName(userDTO.getLastName())
                .cin(userDTO.getCin())
                .email(userDTO.getEmail())
                .nationality(userDTO.getNationality())
                .joinDate(LocalDate.now())
                .licenseExpirationDate(userDTO.getLicenseExpirationDate())
                .build();


        User user =  userRepository.save(newUser);
        String authToken = jwtService.generateToken(user.getUsername());
        return AuthenticationResponse.builder()
                .token(authToken)
                .build();

    }


    public Optional<User> findByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    public AuthenticationResponse login(AuthenticationRequest authenticationRequest) {
        String username = authenticationRequest.getUsername();
        String password = authenticationRequest.getPassword();

       return userRepository.findByUsername(username)
                .map(user -> {
                    if (user.getPassword().equals(password)) {
                        String authToken = jwtService.generateToken(user.getUsername());
                        return AuthenticationResponse.builder()
                                .token(authToken)
                                .build();
                    }
                    return AuthenticationResponse.builder()
                            .token("Invalid credentials.")
                            .build();
                })
                .orElse(AuthenticationResponse.builder()
                        .token("User not found.")
                        .build());
    }
}
