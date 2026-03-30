package com.webgis.admin.dto.user;

import com.webgis.user.User;

/**
 * Data Transfer Object containing all the information about a user that can be retrieved by an admin
 */
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
