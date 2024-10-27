package com.mbarek0.web.huntersleague.controller;

import com.mbarek0.web.huntersleague.dto.AuthenticationRequest;
import com.mbarek0.web.huntersleague.dto.AuthenticationResponse;
import com.mbarek0.web.huntersleague.service.JwtService;
import com.mbarek0.web.huntersleague.dto.UserDTO;
import com.mbarek0.web.huntersleague.service.AuthenticationService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class AuthenticationController {

    private final AuthenticationService authenticationService;
    private final JwtService jwtService;

    @PostMapping("/register")
    public ResponseEntity<AuthenticationResponse> register(@RequestBody @Valid UserDTO userDTO) {
        return ResponseEntity.ok(authenticationService.register(userDTO));
    }



    @PostMapping("/login")
    public ResponseEntity<AuthenticationResponse> login(@RequestBody @Valid AuthenticationRequest request) {

        AuthenticationResponse response = authenticationService.login(request);
        if (response.getToken() == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
        return ResponseEntity.ok(response);
    }

}
