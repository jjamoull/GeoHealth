package com.webgis.user.dto;

import com.webgis.user.User;

public record ResponseDto(
        String username,
        String firstName,
        String lastName,
        String token
) {
    public ResponseDto(User user) {
        this(
                user.getUsername(),
                user.getFirstName(),
                user.getLastName(),
                null
        );
    }
}