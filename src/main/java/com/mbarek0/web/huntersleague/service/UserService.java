package com.mbarek0.web.huntersleague.service;


import com.mbarek0.web.huntersleague.model.enums.Role;
import com.mbarek0.web.huntersleague.web.exception.FieldCannotBeNullException;
import com.mbarek0.web.huntersleague.web.exception.user.UserNotFoundException;
import com.mbarek0.web.huntersleague.web.exception.user.UserNameAlreadyExistsException;
import com.mbarek0.web.huntersleague.model.User;
import com.mbarek0.web.huntersleague.repository.UserRepository;
import com.mbarek0.web.huntersleague.util.PasswordUtil;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.aspectj.apache.bcel.classfile.Field;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.data.domain.PageRequest;

import java.time.LocalDateTime;
import java.util.UUID;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final LocalDateTime licenseExpirationDate = LocalDateTime.now().plusYears(1);
    private final JobProcessorService jobProcessorService;

    public Optional<User> findByUsername(String userName) {
        if (userName == null || userName.isEmpty()) {
           throw new IllegalArgumentException("Username cannot be null");
        }
        return userRepository.findByUsernameAndDeletedFalse(userName);

    }


    public Page<User> getAllUsers(int page, int size) {
        Pageable pageable = PageRequest.of(page, size);

        Page<User> userPage = userRepository.findAll(pageable);

        return userPage;
    }

    public Page<User> searchByUsernameOrCin(String searchKeyword, int page, int size) {
        Pageable pageable = PageRequest.of(page, size);

        return userRepository.findByUsernameContainingOrEmailContainingAndDeletedFalse(searchKeyword, searchKeyword, pageable);
    }

    public Optional<User> findByEmail(String email) {
        if (email == null || email.isEmpty()) {
            throw new IllegalArgumentException("Email cannot be null");
        }
        return userRepository.findByEmailAndDeletedFalse(email);
    }

    public User findUserById(UUID id) {
        if (id == null) {
            throw new IllegalArgumentException("ID cannot be null");
        }

        return userRepository.findById(id)
                .orElseThrow(() -> new UserNotFoundException("User not found"));
    }


    public User createUser(User user) {
        if (user.getRole() == null) throw new FieldCannotBeNullException("Role cannot be null");

        findByUsername(user.getUsername()).ifPresent(value -> {
            throw new UserNameAlreadyExistsException("User with this username already exists");
        });

       findByEmail(user.getEmail()).ifPresent(value -> {
            throw new UserNameAlreadyExistsException("User with this email already exists");
        });

        String password =  PasswordUtil.hashPassword(user.getPassword());
        user.setPassword(password);
        user.setJoinDate(LocalDateTime.now());
        user.setLicenseExpirationDate(licenseExpirationDate);
        user.setRole(Role.MEMBER);

        return userRepository.save(user);
    }


    public User updateUser(User user,UUID id) {
        User existingUser = findUserById(id);

        if (existingUser == null) throw new UserNotFoundException("User not found");
        if (user.getRole() == null) throw   new FieldCannotBeNullException("Role cannot be null");

        if (existingUser.getUsername() != user.getUsername()) {
            findByUsername(user.getUsername()).ifPresent(value -> {
                throw new UserNameAlreadyExistsException("User with this username already exists");
            });
        }

        if (existingUser.getEmail() != user.getEmail()) {
            findByEmail(user.getEmail()).ifPresent(value -> {
                throw new UserNameAlreadyExistsException("User with this email already exists");
            });
        }

        existingUser.setFirstName(user.getFirstName());
        existingUser.setLastName(user.getLastName());
        existingUser.setEmail(user.getEmail());
        existingUser.setRole(user.getRole());
        existingUser.setNationality(user.getNationality());
        existingUser.setCin(user.getCin());
        existingUser.setUsername(user.getUsername());
        existingUser.setPassword(user.getPassword());
        existingUser.setJoinDate(user.getJoinDate());
        existingUser.setLicenseExpirationDate(user.getLicenseExpirationDate());

        return userRepository.save(existingUser);
    }


//  @Transactional
//    public void markCompetitionAsDeleted(UUID competitionId) {
//        // Mark the competition as deleted
//        competitionRepository.findById(competitionId).ifPresent(competition -> {
//            competition.setDeleted(true);
//            competitionRepository.save(competition);
//
//            // Create a job for cascading delete
//            jobProcessorService.createCascadeDeleteJob(competitionId, "COMPETITION");
//        });
//    }

    @Transactional
    public boolean markUserAsDeleted(UUID userId) {
        User user = findUserById(userId);
        if (user == null) {
            throw new UserNotFoundException("User not found");
        }
        user.setDeleted(true);
        userRepository.save(user);
        jobProcessorService.createCascadeDeleteJob(userId, "USER");
        return true;
    }
}
