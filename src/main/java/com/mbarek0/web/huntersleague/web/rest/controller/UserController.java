package com.mbarek0.web.huntersleague.web.rest.controller;

import com.mbarek0.web.huntersleague.model.User;
import com.mbarek0.web.huntersleague.util.Helper;
import com.mbarek0.web.huntersleague.web.vm.mapper.UserVMMapper;
import com.mbarek0.web.huntersleague.web.vm.response.UserVM;
import com.mbarek0.web.huntersleague.model.enums.Role;
import com.mbarek0.web.huntersleague.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.UUID;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/users")
public class UserController {

    private final UserService userService;
    private final UserVMMapper userVMMapper;

    /**
     * Create a new user (Admin-only)
     * @param userVm User to create
     * @param request HTTP request
     * @return Created user
     */
    @PostMapping
    public ResponseEntity<UserVM> createUser(@Valid @RequestBody UserVM userVm, HttpServletRequest request) {
        if (!Helper.isAuthorized(request, Role.ADMIN)) {
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        }
        User user = userVMMapper.userVMToUser(userVm);
        User createdUser = userService.createUser(user);
        UserVM userVM = userVMMapper.toUserVM(createdUser);

        return new ResponseEntity<>(userVM, HttpStatus.CREATED);
    }

    /**
     * Get all users (Admin-only)
     * @param request HTTP request
     * @return List of all users
     */
    @GetMapping
    public ResponseEntity<Page<UserVM>> getAllUsers(HttpServletRequest request,
                                                    @RequestParam(required = false) String searchKeyword,
                                                    @RequestParam(defaultValue = "0") int page,
                                                    @RequestParam(defaultValue = "10") int size) {

        if (!Helper.isAuthorized(request, Role.ADMIN)) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }

        Page<User> users;
        if (searchKeyword == null)
            users = userService.getAllUsers(page, size);
        else
            users =  userService.searchByUsernameOrCin(searchKeyword, page, size);

        List<UserVM> userVMS = users.stream().map(userVMMapper::toUserVM).toList();
        Page<UserVM> userVMPage = new PageImpl<>(userVMS, users.getPageable(), users.getTotalElements());
        return ResponseEntity.ok(userVMPage);
    }

    /**
     * Get a user by ID (Admin, Jury, or the specific user)
     * @param id User ID
     * @param request HTTP request
     * @return User with the specified ID
     */
    @GetMapping("/{id}")
    public ResponseEntity<UserVM> getUserById(@PathVariable String id, HttpServletRequest request) {
        Role role = (Role) request.getAttribute("role");
        String username = (String) request.getAttribute("username");

        UUID userId = UUID.fromString(id);

        User user = userService.findUserById(userId);
        if (user == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        if (role == Role.ADMIN || role == Role.JURY || user.getUsername().equals(username)) {
            UserVM userVM = userVMMapper.toUserVM(user);
            return new ResponseEntity<>(userVM, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        }
    }

    /**
     * Update a user (Admin or the specific user)
     * @param userVm User to update
     * @param request HTTP request
     * @return Updated user
     */
    @PutMapping
    public ResponseEntity<UserVM> updateUser(@Valid @RequestBody UserVM userVm, HttpServletRequest request) {
        Role role = (Role) request.getAttribute("role");
        String username = (String) request.getAttribute("username");

        User existingUser = userService.findUserById(userVm.getId());
        if (existingUser == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        if (role == Role.ADMIN || existingUser.getUsername().equals(username)) {
            User user = userVMMapper.userVMToUser(userVm);
            User updateUser = userService.updateUser(user);
            UserVM userVM = userVMMapper.toUserVM(updateUser);
            return new ResponseEntity<>(userVM, HttpStatus.OK);
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

        if (role != Role.ADMIN && !userService.findUserById(userId).getUsername().equals(username)) {
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        }

        if (!userService.deleteUser(userId)) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }



}
