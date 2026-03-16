package com.webgis.validationform.dto;

/**
 * Data Transfer Object containing the validation form information needed to update a form
 */
public record UpdateValidationFormDto(
        long id,
        Integer agreementLevel,
        String perceivedRisk,
        Integer certaintyLevel,
        String comment,
        boolean isPublic
) {}
