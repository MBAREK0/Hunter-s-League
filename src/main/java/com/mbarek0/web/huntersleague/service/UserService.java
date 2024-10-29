package com.mbarek0.web.huntersleague.service;


import com.mbarek0.web.huntersleague.dto.UserDTO;
import com.mbarek0.web.huntersleague.dto.UserResponse;
import com.mbarek0.web.huntersleague.exception.UserNameAlreadyExistsException;
import com.mbarek0.web.huntersleague.model.User;
import com.mbarek0.web.huntersleague.repository.UserRepository;
import com.mbarek0.web.huntersleague.util.PasswordUtil;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    /**
     * Find a user by username
     * @param userName
     * @return
     */

    public Optional<User> findByUsername(String userName) {
        if (userName == null || userName.isEmpty()) {
           throw new IllegalArgumentException("Username cannot be null");
        }
        return userRepository.findByUsername(userName);

    }

    /**
     * Get all users with pagination support
     * @param page
     * @param size
     * @return
     */

    public UserResponse getAllUserResponse(int page, int size) {
        Pageable pageable = PageRequest.of(page, size);

        Page<UserDTO> userPage = userRepository.findAllUsers(pageable);

        return new UserResponse(userPage.getContent(), userPage.getTotalElements(), userPage.getTotalPages());
    }


    /**
     * Create a new user
     * @param userDTO User to create
     * @return Created user
     */

    @Transactional
    public UserDTO createUser(@Valid UserDTO userDTO) {

        findByUsername(userDTO.getUsername())
                .ifPresent(user -> {
                    throw new UserNameAlreadyExistsException("Username already exists.");
                });

        User newUser = User.builder()
                .username(userDTO.getUsername())
                .password(PasswordUtil.hashPassword(userDTO.getPassword()))
                .firstName(userDTO.getFirstName())
                .lastName(userDTO.getLastName())
                .cin(userDTO.getCin())
                .email(userDTO.getEmail())
                .licenseExpirationDate(userDTO.getLicenseExpirationDate())
                .role(userDTO.getRole())
                .joinDate(userDTO.getJoinDate())
                .nationality(userDTO.getNationality())
                .build();

        User savedUser = userRepository.save(newUser);

        return convertToDTO(savedUser);
    }


    public UserDTO getUserById(UUID id) {
        if (id == null) {
            throw new IllegalArgumentException("ID cannot be null");
        }
        return userRepository.findById(id)
                .map(this::convertToDTO)
                .orElse(null);
    }

  

    private UserDTO convertToDTO(User user) {
        return UserDTO.builder()
                .id(user.getId())
                .username(user.getUsername())
                .firstName(user.getFirstName())
                .lastName(user.getLastName())
                .cin(user.getCin())
                .email(user.getEmail())
                .licenseExpirationDate(user.getLicenseExpirationDate())
                .role(user.getRole())
                .joinDate(user.getJoinDate())
                .nationality(user.getNationality())
                .build();
    }

    public boolean deleteUser(UUID userId) {
        return userRepository.findById(userId)
                .map(user -> {
                    userRepository.delete(user);
                    return true;
                })
                .orElse(false);
    }

    public UserDTO updateUser(UUID userId, @Valid UserDTO userDTO) {
        return null;
    }
}
