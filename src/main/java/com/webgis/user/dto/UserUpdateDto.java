package com.webgis.user.dto;

public record UserUpdateDto (
        String username,
        String firstName,
        String lastName,
        String email
) {}
