package com.mbarek0.web.huntersleague.web.rest.controller;

import com.mbarek0.web.huntersleague.web.vm.request.LoginFormVM;
import com.mbarek0.web.huntersleague.web.vm.response.TokenVM;
import com.mbarek0.web.huntersleague.service.JwtService;
import com.mbarek0.web.huntersleague.web.vm.request.RegisterVM;
import com.mbarek0.web.huntersleague.service.AuthenticationService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthenticationController {

    private final AuthenticationService authenticationService;
    private final JwtService jwtService;



    @PostMapping("/register")
    public ResponseEntity<TokenVM> register(@RequestBody @Valid RegisterVM userDTO) {
        return ResponseEntity.ok(authenticationService.register(userDTO));
    }


    @PostMapping("/login")
    public ResponseEntity<TokenVM> login(@RequestBody @Valid LoginFormVM request) {

        TokenVM response = authenticationService.login(request.getUsername(), request.getPassword());
        if (response.getToken() == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
        return ResponseEntity.ok(response);
    }

}
