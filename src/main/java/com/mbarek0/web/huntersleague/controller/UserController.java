package com.mbarek0.web.huntersleague.controller;

import com.mbarek0.web.huntersleague.dto.UserDTO;
import com.mbarek0.web.huntersleague.dto.UserResponse;
import com.mbarek0.web.huntersleague.model.User;
import com.mbarek0.web.huntersleague.model.enums.Role;
import com.mbarek0.web.huntersleague.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/users")
public class UserController {

    private final UserService userService;


    /**
     * Create a new user (Admin-only)
     * @param userDTO User to create
     * @param request HTTP request
     * @return Created user
     */
    @PostMapping
    public ResponseEntity<UserDTO> createUser(@Valid @RequestBody UserDTO userDTO, HttpServletRequest request) {
        if (!isAuthorized(request, Role.ADMIN)) {
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        }
        UserDTO createdUser = userService.createUser(userDTO);
        return new ResponseEntity<>(createdUser, HttpStatus.CREATED);
    }

    /**
     * Get all users (Admin-only)
     * @param request HTTP request
     * @return List of all users
     */
    @GetMapping
    public ResponseEntity<UserResponse> getAllUsers(
            HttpServletRequest request,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {



        if (!isAuthorized(request, Role.ADMIN)) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }

        UserResponse userResponse = userService.getAllUserResponse( page,  size );

        return ResponseEntity.ok(userResponse);
    }

    /**
     * Get a user by ID (Admin, Jury, or the specific user)
     * @param id User ID
     * @param request HTTP request
     * @return User with the specified ID
     */
    @GetMapping("/{id}")
    public ResponseEntity<UserDTO> getUserById(@PathVariable String id, HttpServletRequest request) {
        Role role = (Role) request.getAttribute("role");
        String username = (String) request.getAttribute("username");

        UUID userId = UUID.fromString(id);

        UserDTO user = userService.getUserById(userId);
        if (user == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        if (role == Role.ADMIN || role == Role.JURY || user.getUsername().equals(username)) {
            return new ResponseEntity<>(user, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        }
    }

    /**
     * Update a user (Admin or the specific user)
     * @param id User ID
     * @param userDTO User to update
     * @param request HTTP request
     * @return Updated user
     */

    @PutMapping("/{id}")
    public ResponseEntity<UserDTO> updateUser(@PathVariable String id, @Valid @RequestBody UserDTO userDTO, HttpServletRequest request) {
        Role role = (Role) request.getAttribute("role");
        String username = (String) request.getAttribute("username");

        UUID userId = UUID.fromString(id);
        UserDTO existingUser = userService.getUserById(userId);
        if (existingUser == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        if (role == Role.ADMIN || existingUser.getUsername().equals(username)) {
            UserDTO updatedUser = userService.updateUser(userId, userDTO);
            return new ResponseEntity<>(updatedUser, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        }
    }

    /**
     * Delete a user (Admin or the specific user)
     * @param id User ID
     * @param request HTTP request
     * @return No content
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable String id, HttpServletRequest request) {
        Role role = (Role) request.getAttribute("role");
        String username = (String) request.getAttribute("username");

        UUID userId = UUID.fromString(id);

        if (role != Role.ADMIN && !userService.getUserById(userId).getUsername().equals(username)) {
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        }

        if (!userService.deleteUser(userId)) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    private boolean isAuthorized(HttpServletRequest request, Role requiredRole) {
        Role role = (Role) request.getAttribute("role");
        return role.equals(requiredRole);
    }
}
