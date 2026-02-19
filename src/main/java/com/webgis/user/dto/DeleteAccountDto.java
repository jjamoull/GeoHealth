package com.webgis.user.dto;

import jakarta.validation.constraints.NotBlank;
/**
 * Data Transfer Object containing needed fields to delete an account.
 */
public record DeleteAccountDto(
        @NotBlank String username,
        @NotBlank String password
) {}
