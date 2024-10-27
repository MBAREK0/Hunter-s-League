package com.mbarek0.web.huntersleague.service;


import com.mbarek0.web.huntersleague.dto.UserDTO;
import com.mbarek0.web.huntersleague.model.User;
import com.mbarek0.web.huntersleague.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
//    private final PasswordEncoder passwordEncoder;

//    public User createUser(UserDTO userDTO) {
//        // Check if the username already exists
//        if (userRepository.findByUsername(userDTO.getUsername()).isPresent()) {
//            throw new RuntimeException("Username already exists."); // Handle this better in production
//        }
//
//        User newUser = User.builder()
//                .username(userDTO.getUsername())
//                .password(passwordEncoder.encode(userDTO.getPassword())) // Encrypting the password
//                .firstName(userDTO.getFirstName())
//                .lastName(userDTO.getLastName())
//                .cin(userDTO.getCin())
//                .email(userDTO.getEmail())
//                .nationality(userDTO.getNationality())
//                .joinDate(LocalDate.now())
//                .licenseExpirationDate(userDTO.getLicenseExpirationDate())
//                .build();
//
//        return userRepository.save(newUser);
//    }

    // Additional methods (Read, Update, Delete) can be added here...
}
