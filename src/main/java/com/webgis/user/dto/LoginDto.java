package com.webgis.user.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

/**
 * Data Transfer Object for login requests.
 */
public record LoginDto (
        @NotBlank @Size(min = 3, max = 20) String username,
        @NotBlank String password
) {
}
