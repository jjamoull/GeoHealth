package com.webgis.admin.dto.user;

/**
 * Data Transfer Object containing needed field to change an account's role.
 */
public record ChangeRoleDto(
        String username,
        String role
) {
}
