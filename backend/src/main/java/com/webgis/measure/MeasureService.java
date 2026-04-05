package com.webgis.measure;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.rcaller.rstuff.RCaller;
import com.github.rcaller.rstuff.RCallerOptions;
import com.github.rcaller.rstuff.RCode;
import com.webgis.evaluationform.EvaluationForm;
import com.webgis.evaluationform.EvaluationFormRepository;
import com.webgis.map.finalmap.FinalMap;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class MeasureService {

    private final EvaluationFormRepository evaluationFormRepository;

    @Value("${script.path}")
    private String scriptPath;

    public MeasureService(EvaluationFormRepository evaluationFormRepository) {
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
     public double computeWeightedEntropyForADivision(FinalMap finalMap, String division, String divisionRisk){

         final List<EvaluationForm> evaluationForms =  evaluationFormRepository
                 .findByFinalMapAndDivisionAndPerceivedRiskIsNotNullAndCertaintyLevelIsNotNullAndIsPublicTrue(
                 finalMap,
                 division);

         if (evaluationForms.isEmpty()) {
             return -1;
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
            default -> 0.0; // should never be reached
        };

    }


    /**
     * Compute mean of WeightedEntropy for a map
     *
     * @param finalMap the map you are interested in
     *
     * @return mean of WeightedEntropy
     */
     public double computeGlobalConsensusIndex(FinalMap finalMap, Map<String,String> riskForDivision){

       final List<String> evaluatedDivisions= evaluationFormRepository.findDivisionsWithPublicEvaluationForms();

       if (evaluatedDivisions.isEmpty()){
           return -1;
       }
       double sum =0;
       for(String division:evaluatedDivisions){
           sum+= computeWeightedEntropyForADivision(finalMap,division, riskForDivision.get(division));
       }

       return sum/evaluatedDivisions.size();
     }

    /**
     * Compute Kippensdroff's Alpha metrics for a map
     *
     * @param finalMap the map you are interested in
     *
     * @return Krippensdroff's Alpha
     */
    public double computekrippendorffAlpha(FinalMap finalMap) throws IOException{
        final List<EvaluationForm> evaluationForms = evaluationFormRepository.findByFinalMap(finalMap);

        final double[][] krippensdorffMatrix = buildKrippensdorffMatrix(evaluationForms);
        System.out.println("In compute Krippendorff");

        RCode code = RCode.create();

        code.addDoubleMatrix("krippensdorff_matrix", krippensdorffMatrix);
        code.addRCode("library(irr)");
        code.addRCode("result <- kripp.alpha(krippensdorff_matrix,\"ordinal\")");
        code.addRCode("alpha_value <- result$value");

        RCallerOptions options = RCallerOptions.create();
        RCaller caller = RCaller.create(code, RCallerOptions.create());
        caller.runAndReturnResult("alpha_value");

        return caller.getParser().getAsDoubleArray("alpha_value")[0];
    }

    /**
     * Build the matrix needed to compute Krippendorff's alpha measure
     *
     * @param evaluationForms all the evaluation forms of the map you are interested in
     *
     * @return the matrix required to compute the krippendorff measure
     *         (row: evaluator,column: evaluation for a division)
     */
    private double[][] buildKrippensdorffMatrix(List<EvaluationForm> evaluationForms){
        final List<Long> usersIdList= evaluationForms.stream()
                .map(form->form.getUser().getId())
                .distinct()
                .sorted()
                .toList();

        final List<String> evaluatedDivisionsList= evaluationForms.stream()
                .map(form->form.getDivision())
                .distinct()
                .sorted()
                .toList();

        final Map<Long,Map<String,Integer>> krippendorffHashMap = buildKrippendorffHashMap(evaluationForms);

        int rows = usersIdList.size();
        int cols = evaluatedDivisionsList.size();
        double[][] matrix = new double[rows][cols];

        for (int i = 0; i < rows; i++) {
            long userId = usersIdList.get(i);

            for (int j = 0; j < cols; j++) {
                String division = evaluatedDivisionsList.get(j);

                Integer value = null;
                if (krippendorffHashMap.containsKey(userId)) {
                    value = krippendorffHashMap.get(userId).get(division);
                }

                if (value == null) {
                    matrix[i][j] = Double.NaN;
                } else {
                    matrix[i][j] = value.doubleValue();
                }
            }
        }
        return matrix;
    }

    /**
     * Build the hashmap used to infer the Krippendorff's alpha matrix
     *
     * @param evaluationForms all the evaluation forms of the map you are interested in
     *
     * @return a Map containing for each user (id) the divisions the user evaluated and the risk perceived for these divisions
     */
    private Map<Long,Map<String,Integer>> buildKrippendorffHashMap(List<EvaluationForm> evaluationForms){
        final Map<Long,Map<String,Integer>> krippendorffHashMap= new HashMap<>();

        for(EvaluationForm form: evaluationForms){
            if(!krippendorffHashMap.containsKey(form.getUser().getId())){
                krippendorffHashMap.put(form.getUser().getId(), new HashMap<>());
            }
            krippendorffHashMap
                    .get(form.getUser().getId())
                    .put(form.getDivision(), RiskLevel.fromString(form.getPerceivedRisk()).getScore());

        }
        return krippendorffHashMap;
    }

}
