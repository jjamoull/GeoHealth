package com.webgis.user.dto;

/**
 * Data Transfer Object containing needed fields to update a user's password.
 */
public record UpdatePasswordDto(
        String username,
        String oldPassword,
        String newPassword
) {}
