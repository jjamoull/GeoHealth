package com.webgis.measure.measureservice;

import com.webgis.evaluationform.EvaluationForm;
import com.webgis.evaluationform.EvaluationFormRepository;
import com.webgis.map.finalmap.FinalMap;
import com.webgis.measure.RiskLevel;
import org.springframework.stereotype.Service;

import java.util.EnumMap;
import java.util.List;
import java.util.Map;

@Service
public class MeanMesureService {

    private final EvaluationFormRepository evaluationFormRepository;

    public MeanMesureService(EvaluationFormRepository evaluationFormRepository) {
        this.evaluationFormRepository = evaluationFormRepository;
    }


    /**
     * Compute the mean divisional agreement score for a specific division of a map
     * (Average of the agreement score (from 1 to 4) given by the evaluators for a specific division of a map)
     *
     * @param finalMap the map you are interested in
     * @param division the division you are interested in
     *
     * @return mean divisional agreement score
     */
    public double computeMeanDivisionalAgreementScore(FinalMap finalMap, String division) {
        final List<Double> agreementLevels = evaluationFormRepository
                .findByFinalMapAndDivisionAndAgreementLevelIsNotNullAndIsPublicTrue(finalMap, division)
                .stream()
                .map(evaluationForm -> Double.valueOf(evaluationForm.getAgreementLevel()))
                .toList();

        return computeMeanFromValues(agreementLevels);
    }


    /**
     * Compute the mean certainty for a specific division of a map
     *
     * @param finalMap the map you are interested in
     * @param division the division you are interested in
     *
     * @return mean certainty for a specific division of a map
     */
    public double computeMeanCertaintyForMapForDivision(FinalMap finalMap, String division) {
        final List<Double> certaintyLevels = evaluationFormRepository
                .findByFinalMapAndDivisionAndCertaintyLevelIsNotNullAndIsPublicTrue(finalMap, division)
                .stream()
                .map(evaluationForm -> Double.valueOf(evaluationForm.getCertaintyLevel()))
                .toList();

        return computeMeanFromValues(certaintyLevels);
    }

    /**
     * Compute the mean for a list
     *
     * @param values the list of value from which you want the mean to be computed
     *
     * @return the mean of values
     */
    private double computeMeanFromValues(List<Double> values) {
        if (values.isEmpty()) return 0;

        double sum = 0;
        for (double v : values) {
            sum += v;
        }
        return sum / values.size();
    }

    /**
     * Compute the dominant perceived RiskLevel for a specific division of a map
     * (The risk category (low, medium, high) that is given the greatest weighting
     *  after taking into account the evaluators’ level of certainty.)
     *
     * @param finalMap the map you are interested in
     * @param division the division you are interested in
     *
     * @return dominant perceived RiskLevel for a specific division of a map
     */
    public RiskLevel computeDominantPerceivedRiskLevelForMapForDivision(FinalMap finalMap, String division) {

        final List<EvaluationForm> evaluationForms = evaluationFormRepository
                .findByFinalMapAndDivisionAndPerceivedRiskIsNotNullAndCertaintyLevelIsNotNullAndIsPublicTrue(
                        finalMap,
                        division);

        final EnumMap<RiskLevel, Double> weightForRiskLevel = new EnumMap<>(RiskLevel.class);

        for (EvaluationForm evaluationForm : evaluationForms) {
            final RiskLevel riskLevel = RiskLevel.fromString(evaluationForm.getPerceivedRisk());
            final double weight = evaluationForm.getCertaintyLevel() / 4.0;

            weightForRiskLevel.merge(riskLevel, weight, Double::sum);
        }

        return weightForRiskLevel.entrySet().stream()
                .max(Map.Entry.comparingByValue())
                .map(Map.Entry::getKey)
                .orElse(null);
    }
}
