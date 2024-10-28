package com.mbarek0.web.huntersleague.controller;

import com.mbarek0.web.huntersleague.dto.UserDTO;
import com.mbarek0.web.huntersleague.model.enums.Role;
import com.mbarek0.web.huntersleague.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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
        if (isAuthorized(request, Role.ADMIN)) {
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
    public ResponseEntity<List<UserDTO>> getAllUsers(HttpServletRequest request) {
        if (isAuthorized(request, Role.ADMIN)) {
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        }
        List<UserDTO> users = userService.getAllUsers();
        return new ResponseEntity<>(users, HttpStatus.OK);
    }

    /**
     * Get a user by ID (Admin, Jury, or the specific user)
     * @param id User ID
     * @param request HTTP request
     * @return User with the specified ID
     */
    @GetMapping("/{id}")
    public ResponseEntity<UserDTO> getUserById(@PathVariable Long id, HttpServletRequest request) {
        Role role = (Role) request.getAttribute("role");
        String username = (String) request.getAttribute("username");

        UserDTO user = userService.getUserById(id);
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
    public ResponseEntity<UserDTO> updateUser(@PathVariable Long id, @Valid @RequestBody UserDTO userDTO, HttpServletRequest request) {
        Role role = (Role) request.getAttribute("role");
        String username = (String) request.getAttribute("username");

        UserDTO existingUser = userService.getUserById(id);
        if (existingUser == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        if (role == Role.ADMIN || existingUser.getUsername().equals(username)) {
            UserDTO updatedUser = userService.updateUser(id, userDTO);
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
    public ResponseEntity<Void> deleteUser(@PathVariable Long id, HttpServletRequest request) {
        Role role = (Role) request.getAttribute("role");
        String username = (String) request.getAttribute("username");

        if (role != Role.ADMIN && !userService.getUserById(id).getUsername().equals(username)) {
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        }

        if (!userService.deleteUser(id)) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    private boolean isAuthorized(HttpServletRequest request, Role requiredRole) {
        Role role = (Role) request.getAttribute("role");
        return role != requiredRole;
    }
}
