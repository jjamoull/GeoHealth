package com.webgis.evaluationform.dto;

/**
 * Data Transfer Object containing the validation form information needed to update a form
 */
public record UpdateEvaluationFormDto(
        long id,
        Integer agreementLevel,
        String perceivedRisk,
        Integer certaintyLevel,
        String comment,
        boolean isPublic
) {}
