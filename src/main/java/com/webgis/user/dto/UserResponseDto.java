package com.webgis.user.dto;

import com.webgis.user.User;

public record UserResponseDto(
        String username,
        String firstName,
        String lastName
) {
    public UserResponseDto(User user) {
        this(
            user.getUsername(),
            user.getFirstName(),
            user.getLastName()
        );
    }
}