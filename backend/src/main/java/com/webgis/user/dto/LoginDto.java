package com.webgis.user.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

/**
 * Data Transfer Object containing needed fields to logging a user.
 */
public record LoginDto (
        @NotBlank @Size(min = 3, max = 20) String username,
        @NotBlank String password
) {
}
