package com.mbarek0.web.huntersleague.model;

import com.mbarek0.web.huntersleague.model.enums.Role;
import jakarta.persistence.*;
import lombok.*;


import java.util.UUID;

@Entity
@Table(name = "users")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class User  {

    @Id
    @GeneratedValue
    private UUID id;

    @Column(name = "username")
    private String username;

    @Column(name = "password")
    private String password;

    @Enumerated(EnumType.STRING)
    private Role role;

    @Column(name = "first_name")
    private String firstName;

    @Column(name = "last_name")
    private String lastName;

    @Column(name = "cin")
    private String cin;

    @Column(name = "email")
    private String email;

    @Column(name = "nationality")
    private String nationality;

    @Column(name = "join_date")
    private java.time.LocalDate joinDate;

    @Column(name = "license_expiration_date")
    private java.time.LocalDate licenseExpirationDate;


}
