package com.webgis.user.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

/**
 * Data Transfer Object for registration requests.
 * Contains all required fields for registration requests.
 */
public record RegisterDto(
        @NotBlank @Size(min = 3, max = 20) String username,
        @NotBlank String firstName,
        @NotBlank String lastName,
        @Email @NotBlank String email,
        @NotBlank @Size(min = 8) String password
) {

}