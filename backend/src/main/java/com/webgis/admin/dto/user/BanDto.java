package com.webgis.admin.dto.user;

/**
 * Data Transfer Object containing needed fields to ban an account.
 */
public record BanDto(
        String username,
        String reason
) {}
