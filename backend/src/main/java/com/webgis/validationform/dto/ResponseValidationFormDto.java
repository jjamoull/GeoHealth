package com.webgis.validationform.dto;

import com.webgis.validationform.ValidationForm;

/**
 * Data Transfer Object containing all the validation form information
 */
public record ResponseValidationFormDto(
        long id,
        String division,
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
                validationForm.getDivision(),
                validationForm.getAgreementLevel(),
                validationForm.getPerceivedRisk(),
                validationForm.getCertaintyLevel(),
                validationForm.getComment(),
                validationForm.getUser().getUsername(),
                validationForm.isPublic()
        );
    }
}
