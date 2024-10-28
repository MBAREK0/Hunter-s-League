package com.mbarek0.web.huntersleague.dto;



import jakarta.validation.constraints.*;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserDTO {

    @NotBlank(message = "Username is required.")
    private String username;

    @NotBlank(message = "Password is required.")
    @Size(min = 8, message = "Password must be at least 8 characters long.")
    @Pattern(regexp = ".*[a-z].*", message = "Password must contain at least one lowercase letter.")
    @Pattern(regexp = ".*[A-Z].*", message = "Password must contain at least one uppercase letter.")
    @Pattern(regexp = ".*[0-9].*", message = "Password must contain at least one digit.")
    private String password;

    @NotBlank(message = "First name is required.")
    private String firstName;

    @NotBlank(message = "Last name is required.")
    private String lastName;

    @NotBlank(message = "CIN is required.")
    private String cin;

    @Email(message = "Email should be valid.")
    @NotBlank(message = "Email is required.")
    private String email;

    @NotBlank(message = "Nationality is required.")
    private String nationality;

    private LocalDateTime joinDate;

    @Future(message = "License expiration date must be in the future.")
    private LocalDateTime licenseExpirationDate;
}