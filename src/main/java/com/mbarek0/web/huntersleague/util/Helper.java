package com.mbarek0.web.huntersleague.util;

import com.mbarek0.web.huntersleague.model.enums.Role;
import jakarta.servlet.http.HttpServletRequest;

public  class Helper {

    /**
     * Check if the user is authorized to perform the action
     * @param request HTTP request
     * @param requiredRole Required role
     * @return True if the user is authorized, false otherwise
     */
    public static boolean isAuthorized(HttpServletRequest request, Role requiredRole) {
        Role role = (Role) request.getAttribute("role");
        return role.equals(requiredRole);
    }
}
