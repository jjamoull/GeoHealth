package com.webgis.measure.measureservice;

import com.webgis.evaluationform.EvaluationForm;
import com.webgis.evaluationform.EvaluationFormRepository;
import com.webgis.map.finalmap.FinalMap;
import com.webgis.measure.RiskLevel;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class ModelEvaluationMeasureService {

    private final EvaluationFormRepository evaluationFormRepository;

    public ModelEvaluationMeasureService(EvaluationFormRepository evaluationFormRepository) {
        this.evaluationFormRepository = evaluationFormRepository;
    }

    /**
     * Compute the weighted degree of consensus among experts for a map for a division
     *
     * @param finalMap the map you are interested in
     * @param division the division you are interested in
     * @param divisionRisk the risk level in the division as evaluated in the original map
     * @return weighted degree of consensus among experts
     */
    public double computeWeightedDivisionalLevelAgreementScore(FinalMap finalMap, String division, String divisionRisk){

        final List<EvaluationForm> evaluationForms = evaluationFormRepository
                .findByFinalMapAndDivisionAndPerceivedRiskIsNotNullAndCertaintyLevelIsNotNullAndIsPublicTrue(
                        finalMap,
                        division);

        if (evaluationForms.isEmpty()) {
            return 0;
        }

        double sum =0;
        for(EvaluationForm evaluationForm:evaluationForms){
            sum+= computeAgreementScore(divisionRisk,evaluationForm.getPerceivedRisk())*((double) evaluationForm.getCertaintyLevel()/4);
        }
        return sum/evaluationForms.size();
    }

    /**
     * Compute the agreement score between two evaluated riskLevel
     *
     * @param divisionRisk the risk level in the division as evaluated in the original map
     * @param perceivedRisk the risk level in the division as evaluated by the expert
     *
     * @return agreement score (0,0.5,1)
     */
    private double computeAgreementScore(String divisionRisk, String perceivedRisk) {
        final RiskLevel divisionRiskLevel = RiskLevel.fromString(divisionRisk);
        final RiskLevel perceivedRiskLevel = RiskLevel.fromString(perceivedRisk);

        final int diff = Math.abs(divisionRiskLevel.getScore() - perceivedRiskLevel.getScore());

        return switch (diff) {
            case 0 -> 1.0;
            case 1 -> 0.5;
            case 2 -> 0.0;
            default -> 0.0;

        };

    }


    /**
     * Compute mean of WeightedEntropy for a map
     *
     * @param finalMap the map you are interested in
     *
     * @return mean of WeightedEntropy
     */
    public double computeNationalModelFieldAgreementScore(FinalMap finalMap, Map<String,String> riskForDivision){

        final List<String> divisions= evaluationFormRepository.findDivisionsWithValidPublicEvaluationForms(finalMap);

        if(divisions.isEmpty()){
            return 0.0;
        }
        double sum =0;
        for(String division:divisions){
            sum+= computeWeightedDivisionalLevelAgreementScore(finalMap,division, riskForDivision.get(division));
        }

        return sum/divisions.size();
    }

}
