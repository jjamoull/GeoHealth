package com.webgis.validationform.dto;

/**
 * Data Transfer Object containing the validation form information needed to save a form
 */
public record SaveValidationFormDto(
    String division,
    Integer agreementLevel,
    String perceivedRisk,
    Integer certaintyLevel,
    String comment,
    long finalMapId,
    boolean isPublic){}
