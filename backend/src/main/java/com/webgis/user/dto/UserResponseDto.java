package com.webgis.user.dto;

import com.webgis.user.User;

/**
 * Data Transfer Object for the backend to send needed fields to the frontend.
 */
public record UserResponseDto(
        String username,
        String firstName,
        String lastName,
        String email,
        String role
) {
    public UserResponseDto(User user) {
        this(
            user.getUsername(),
            user.getFirstName(),
            user.getLastName(),
            user.getEmail(),
            user.getRole()
        );
    }
}