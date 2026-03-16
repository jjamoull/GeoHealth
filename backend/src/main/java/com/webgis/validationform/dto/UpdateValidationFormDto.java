package com.webgis.validationform.dto;


public record UpdateValidationFormDto(
        long id,
        Integer agreementLevel,
        String perceivedRisk,
        Integer certaintyLevel,
        String comment,
        boolean isPublic
) {}
