package com.mbarek0.web.huntersleague.repository;

import com.mbarek0.web.huntersleague.dto.UserDTO;
import com.mbarek0.web.huntersleague.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface UserRepository extends JpaRepository<User, UUID> {

    Optional<User> findByUsername(String username);

    @Query("SELECT new com.mbarek0.web.huntersleague.dto.UserDTO(u.id, u.username, u.firstName, u.lastName, u.role, u.cin, u.email, u.joinDate, u.licenseExpirationDate) " +
            "FROM User u")
    Page<UserDTO> findAllUsers(Pageable pageable);

    User save(UserDTO userDTO);
}