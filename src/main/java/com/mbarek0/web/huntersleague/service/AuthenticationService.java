package com.mbarek0.web.huntersleague.service;

import com.mbarek0.web.huntersleague.model.enums.Role;
import com.mbarek0.web.huntersleague.web.exception.user.UserNameAlreadyExistsException;
import com.mbarek0.web.huntersleague.web.exception.user.UsernameOrPasswordInvalidException;
import com.mbarek0.web.huntersleague.web.vm.mapper.UserVMMapper;
import com.mbarek0.web.huntersleague.web.vm.response.TokenVM;
import com.mbarek0.web.huntersleague.web.vm.request.RegisterVM;
import com.mbarek0.web.huntersleague.model.User;
import com.mbarek0.web.huntersleague.repository.UserRepository;
import io.jsonwebtoken.ExpiredJwtException;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class AuthenticationService {
    private final UserRepository userRepository;
    private final JwtService jwtService;
    private final UserVMMapper userVMMapper;
    private final UserService userService;
    private final PasswordEncoder passwordEncoder; // Inject PasswordEncoder
    private final LocalDateTime licenseExpirationDate = LocalDateTime.now().plusYears(1);

    /**
     * Registers a new user.
     * @param registerVM the registration data
     * @return TokenVM containing the generated token for the new user
     */
    public TokenVM register(@Valid RegisterVM registerVM) {
        // Validate username and email uniqueness
        userService.findByUsername(registerVM.getUsername())
                .ifPresent(existingUser -> {
                    throw new UserNameAlreadyExistsException("Username already exists");
                });

        userService.findByEmail(registerVM.getEmail())
                .ifPresent(existingUser -> {
                    throw new UserNameAlreadyExistsException("Email already exists");
                });

        // Map RegisterVM to User entity
        User newUser = userVMMapper.registerVMtoUser(registerVM);

        // Set default values
        newUser.setPassword(passwordEncoder.encode(newUser.getPassword())); // Use PasswordEncoder
        newUser.setJoinDate(LocalDateTime.now());
        newUser.setLicenseExpirationDate(licenseExpirationDate);
        newUser.setRole(Role.MEMBER); // Default role: MEMBER

        // Save user and generate token
        User savedUser = userRepository.save(newUser);
        String authToken = jwtService.generateToken(savedUser.getUsername());
        String refreshToken = jwtService.generateRefreshToken(savedUser.getUsername());

        // Return the token in a response object
        return TokenVM.builder().token(authToken).refreshToken(refreshToken).build();
    }

    /**
     * Authenticates a user by validating username and password.
     * @param username the username
     * @param password the plaintext password
     * @return TokenVM containing the generated token for the authenticated user
     */
    public TokenVM login(String username, String password) {
        return userRepository.findByUsernameAndDeletedFalse(username)
                .filter(user -> passwordEncoder.matches(password, user.getPassword())) // Use PasswordEncoder
                .map(authenticatedUser -> {
                    // Generate JWT token for the authenticated user
                    String authToken = jwtService.generateToken(authenticatedUser.getUsername());
                    String refreshToken = jwtService.generateRefreshToken(authenticatedUser.getUsername());
                    return TokenVM.builder()
                            .token(authToken)
                            .refreshToken(refreshToken)
                            .build();
                })
                .orElseThrow(() -> new UsernameOrPasswordInvalidException("Invalid credentials."));
    }


    public TokenVM refresh(String refreshToken) {

       if(jwtService.isTokenExpired(refreshToken)) {
            throw new ExpiredJwtException(null, null, "Refresh token has expired");
        }
        String username = jwtService.extractUsername(refreshToken);

        if (!jwtService.isTokenValid(refreshToken,username )) {
            throw new UsernameOrPasswordInvalidException("Invalid refresh token");
        }

        String newAccessToken = jwtService.generateToken(username);


        return new TokenVM(newAccessToken, refreshToken);
    }
}
