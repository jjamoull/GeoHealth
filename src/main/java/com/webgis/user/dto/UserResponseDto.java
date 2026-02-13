package com.webgis.user.dto;

import com.webgis.user.User;

/**
 * Data Transfer Object for User responses.
 * Contains required information after successful login or registration.
 */
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