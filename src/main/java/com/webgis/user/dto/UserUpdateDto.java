package com.webgis.user.dto;

/**
 * Data Transfer Object containing needed fields to update user's information.
 */
public record UserUpdateDto (
        String username,
        String firstName,
        String lastName,
        String email
) {}
