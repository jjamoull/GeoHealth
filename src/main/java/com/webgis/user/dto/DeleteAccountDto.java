package com.webgis.user.dto;

import jakarta.validation.constraints.NotBlank;

public record DeleteAccountDto(
        @NotBlank String username,
        @NotBlank String password
) {}
