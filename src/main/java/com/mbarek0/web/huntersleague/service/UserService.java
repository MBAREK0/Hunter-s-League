package com.mbarek0.web.huntersleague.service;


import com.mbarek0.web.huntersleague.dto.UserDTO;
import com.mbarek0.web.huntersleague.model.User;
import com.mbarek0.web.huntersleague.repository.UserRepository;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    public Optional<User> findByUsername(String userName) {
        if (userName == null || userName.isEmpty()) {
           throw new IllegalArgumentException("Username cannot be null");
        }
        return userRepository.findByUsername(userName);

    }


    public UserDTO createUser(@Valid UserDTO userDTO) {
    }

    public List<UserDTO> getAllUsers() {
    }

    public UserDTO getUserById(Long id) {
    }

    public UserDTO updateUser(Long id, @Valid UserDTO userDTO) {
    }

    public boolean deleteUser(Long id) {
    }
}
