package com.webgis.admin.dto.evaluationform;

import com.webgis.evaluationform.EvaluationForm;

/**
 * Data Transfer Object containing all the evaluation form information for admin
 */
public record AdminResponseEvaluationFormDto(
        long id,
        String division,
        Integer agreementLevel,
        String perceivedRisk,
        Integer certaintyLevel,
        String comment,
        String username,
        String firstName,
        String lastName,
        boolean isPublic
) {
    public AdminResponseEvaluationFormDto(EvaluationForm evaluationForm) {
        this(
                evaluationForm.getId(),
                evaluationForm.getDivision(),
                evaluationForm.getAgreementLevel(),
                evaluationForm.getPerceivedRisk(),
                evaluationForm.getCertaintyLevel(),
                evaluationForm.getComment(),
                evaluationForm.getUser().getUsername(),
                evaluationForm.getUser().getFirstName(),
                evaluationForm.getUser().getLastName(),
                evaluationForm.isPublic()
        );
    }
}