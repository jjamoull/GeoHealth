package com.webgis.validationform.dto;

import com.webgis.user.User;

public record UpdateValidationFormDto(
        long id,
        Integer agreementLevel,
        String perceivedRisk,
        Integer certaintyLevel,
        String comment,
        boolean isPublic
) {}
