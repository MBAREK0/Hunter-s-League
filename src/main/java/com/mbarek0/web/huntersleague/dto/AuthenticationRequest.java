package com.mbarek0.web.huntersleague.dto;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;

public class AuthenticationRequest {


    @NotBlank(message = "Username cannot be blank")
    private String username;
    @NotBlank(message = "Password cannot be blank")
    private String password;

    public AuthenticationRequest() {
    }

    public AuthenticationRequest(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }
}
