package com.webgis.evaluationform.dto;

import com.webgis.evaluationform.EvaluationForm;

/**
 * Data Transfer Object containing all the validation form information
 */
public record ResponseEvaluationFormDto(
        long id,
        String division,
        Integer agreementLevel,
        String perceivedRisk,
        Integer certaintyLevel,
        String comment,
        String username,
        boolean isPublic
){
    public ResponseEvaluationFormDto(EvaluationForm evaluationForm){
        this(
                evaluationForm.getId(),
                evaluationForm.getDivision(),
                evaluationForm.getAgreementLevel(),
                evaluationForm.getPerceivedRisk(),
                evaluationForm.getCertaintyLevel(),
                evaluationForm.getComment(),
                evaluationForm.getUser().getUsername(),
                evaluationForm.isPublic()
        );
    }
}
