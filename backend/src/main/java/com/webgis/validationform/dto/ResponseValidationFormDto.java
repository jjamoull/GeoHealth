package com.webgis.validationform.dto;

import com.webgis.validationform.ValidationForm;

/**
 * Data Transfer Object containing the all validation form information
 */
public record ResponseValidationFormDto(
        long id,
        String department,
        Integer agreementLevel,
        String perceivedRisk,
        Integer certaintyLevel,
        String comment,
        String username,
        boolean isPublic
){
    public ResponseValidationFormDto(ValidationForm validationForm){
        this(
                validationForm.getId(),
                validationForm.getDepartment(),
                validationForm.getAgreementLevel(),
                validationForm.getPerceivedRisk(),
                validationForm.getCertaintyLevel(),
                validationForm.getComment(),
                validationForm.getUser().getUsername(),
                validationForm.isPublic()
        );
    }
}
