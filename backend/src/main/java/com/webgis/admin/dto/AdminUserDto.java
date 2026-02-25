package com.webgis.admin.dto;

import com.webgis.user.User;

public record AdminUserDto(
        long id,
        String username,
        String firstName,
        String lastName,
        String email,
        String role,
        boolean deleted,
        boolean banned
) {
    public AdminUserDto(User user) {
        this(
                user.getId(),
                user.getUsername(),
                user.getFirstName(),
                user.getLastName(),
                user.getEmail(),
                user.getRole(),
                user.isDeleted(),
                user.isBanned()
        );
    }
}
