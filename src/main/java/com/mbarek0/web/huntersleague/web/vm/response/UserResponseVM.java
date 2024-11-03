package com.mbarek0.web.huntersleague.web.vm.response;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.UUID;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class UserResponseVM {

    private UUID id;

    private String username;

    private String password;

    private String firstName;

    private String lastName;

    private String role;

    private String cin;

    private String email;

    private String nationality;

}