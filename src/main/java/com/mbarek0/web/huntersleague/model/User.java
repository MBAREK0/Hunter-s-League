package com.mbarek0.web.huntersleague.model;


import com.mbarek0.web.huntersleague.model.enums.Role;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    private String username;

    private String password;

    @Enumerated(EnumType.STRING)
    private Role role;

    private String firstName;

    private String lastName;

    private String cin;

    private String email;

    private String nationality;

    private LocalDateTime joinDate;

    private LocalDateTime licenseExpirationDate;

    @OneToMany(mappedBy = "user")
    private List<Participation> participations;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return Objects.equals(id, user.id) && Objects.equals(username, user.username) && Objects.equals(password, user.password) && role == user.role && Objects.equals(firstName, user.firstName) && Objects.equals(lastName, user.lastName) && Objects.equals(cin, user.cin) && Objects.equals(email, user.email) && Objects.equals(nationality, user.nationality) && Objects.equals(joinDate, user.joinDate) && Objects.equals(licenseExpirationDate, user.licenseExpirationDate) && Objects.equals(participations, user.participations);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, username, password, role, firstName, lastName, cin, email, nationality, joinDate, licenseExpirationDate);
    }
}
