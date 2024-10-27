package com.mbarek0.web.huntersleague.util;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

public class PasswordUtil {
    private static final BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    // Hash the password
    public static String hashPassword(String password) {
        return passwordEncoder.encode(password);
    }

    // Verify the password
    public static boolean checkPassword(String rawPassword, String hashedPassword) {
        return passwordEncoder.matches(rawPassword, hashedPassword);
    }
}
