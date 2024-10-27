package com.mbarek0.web.huntersleague.controller;

import com.mbarek0.web.huntersleague.dto.UserDTO;
import com.mbarek0.web.huntersleague.model.User;
import com.mbarek0.web.huntersleague.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/v1/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @GetMapping("/test")
    public String getUser() {
        return "User with ID: " ;
    }

}
