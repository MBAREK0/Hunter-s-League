package com.mbarek0.web.huntersleague.dto;


import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class UserResponse {
    private List<UserDTO> users;
    private long totalElements;
    private int totalPages;
}