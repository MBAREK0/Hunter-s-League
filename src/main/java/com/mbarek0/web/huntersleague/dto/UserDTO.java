package com.mbarek0.web.huntersleague.dto;



import com.mbarek0.web.huntersleague.model.enums.Role;
import jakarta.validation.constraints.*;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserDTO {


    private UUID id;
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

    private Role role;

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


    public UserDTO(UUID id, String username, String firstName, String lastName, Role role, String cin, String email, LocalDateTime joinDate, LocalDateTime licenseExpirationDate) {
        this.id = id;
        this.username = username;
        this.firstName = firstName;
        this.lastName = lastName;
        this.role = role;
        this.cin = cin;
        this.email = email;
        this.joinDate = joinDate;
        this.licenseExpirationDate = licenseExpirationDate;
    }
}
