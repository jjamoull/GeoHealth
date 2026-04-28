package com.webgis.report;

import com.webgis.map.finalmap.FinalMap;
import com.webgis.map.finalmap.MapTag;
import com.webgis.measure.RiskLevel;
import com.webgis.measure.measureservice.EvaluatorAgreementMeasureService;
import com.webgis.measure.measureservice.MeanMesureService;
import com.webgis.measure.measureservice.ModelEvaluationMeasureService;

import java.util.HashMap;
import java.util.Map;

public class MeasureHolder {

    //Services
    private final EvaluatorAgreementMeasureService evaluatorAgreementMeasureService;
    private final  MeanMesureService meanMesureService;
    private final ModelEvaluationMeasureService modelEvaluationMeasureService;

    //Evaluator Agreement metrics
    private final Map<String, Double> divisionalWeightedEntropy = new HashMap<>();
    private final Map<String, Double> divisionalConsensusScore = new HashMap<>();
    private Double nationalAverageEntropy;
    private Double nationalConsensusScore;
    private Double krippendorff;

    //Mean measure metrics
    private final Map<String, Double> meanAgreementScoreForDivison = new HashMap<>();
    private final Map<String, Double> meanCertaintyForForDivision = new HashMap<>();
    private final Map<String, RiskLevel> dominantPerceivedRiskLevelForDivison = new HashMap<>();

    //Model evaluation measure service
    private final Map<String, Double> weightedDivisionalLevelAgreementScore = new HashMap<>();
    private Double nationalModelFieldAgreementScore;


    public MeasureHolder( EvaluatorAgreementMeasureService evaluatorAgreementMeasureService,
                          MeanMesureService meanMesureService,
                          ModelEvaluationMeasureService modelEvaluationMeasureService){

        this.evaluatorAgreementMeasureService=evaluatorAgreementMeasureService;
        this.meanMesureService=meanMesureService;
        this.modelEvaluationMeasureService= modelEvaluationMeasureService;}

    /**
     * Compute all the metrics for a specific map
     *
     * @param finalMap the map you are interested in
     * @param riskForDivision a map containing the risk evaluated in the model(original map) for each division
     */
    public void computeAllMeasure(FinalMap finalMap,Map<String, String> riskForDivision){
        computeEvaluatorAgreement(finalMap,riskForDivision);
        computeMeanMeasures(finalMap,riskForDivision);
        if(!finalMap.getTags().contains(MapTag.EBOLA)){
            computeModelEvaluation(finalMap,riskForDivision);
        }
    }

    /**
     * Compute all the evaluator agreement related metrics
     *
     * @param finalMap the map you are interested in
     * @param riskForDivision a map containing the risk evaluated in the model (original map) for each division
     */
    private void computeEvaluatorAgreement(FinalMap finalMap,Map<String, String> riskForDivision){

        //National metrics
        krippendorff= this.evaluatorAgreementMeasureService.computekrippendorffAlpha(finalMap);

        nationalAverageEntropy= this.evaluatorAgreementMeasureService
               .computeNationalAverageEntropy(finalMap);
        nationalConsensusScore= this.evaluatorAgreementMeasureService
               .computeNationalConsensusScore(finalMap);

        //Divisional metrics
        for (String division:riskForDivision.keySet()){
           divisionalWeightedEntropy.put(division,
                   evaluatorAgreementMeasureService.computeDivisionalWeightedEntropy(finalMap,division));

           divisionalConsensusScore.put(division,
                   evaluatorAgreementMeasureService.computeDivisionalConsensusScore(finalMap,division));

       }
    }

    /**
     * Compute all the mean measure metrics
     *
     * @param finalMap the map you are interested in
     * @param riskForDivision a map containing the risk evaluated in the model (original map) for each division
     */
    private void computeMeanMeasures(FinalMap finalMap,Map<String, String> riskForDivision){

        for (String division:riskForDivision.keySet()){
            meanAgreementScoreForDivison.put(division,
                    meanMesureService.computeMeanDivisionalAgreementScore(finalMap,division));

            meanCertaintyForForDivision.put(division,
                    meanMesureService.computeMeanCertaintyForMapForDivision(finalMap,division));

            dominantPerceivedRiskLevelForDivison.put(division,
                    meanMesureService.computeDominantPerceivedRiskLevelForMapForDivision(finalMap,division));
        }
    }

    /**
     * Compute all the model evaluation metrics
     *
     * @param finalMap the map you are interested in
     * @param riskForDivision a map containing the risk evaluated in the model (original map) for each division
     */
    private void computeModelEvaluation(FinalMap finalMap,Map<String, String> riskForDivision){

        //National metric
        nationalModelFieldAgreementScore= modelEvaluationMeasureService
                .computeNationalModelFieldAgreementScore(finalMap,riskForDivision);

        //Divisional metric
        for (String division:riskForDivision.keySet()){
            weightedDivisionalLevelAgreementScore.put(
                    division,
                    modelEvaluationMeasureService.computeWeightedDivisionalLevelAgreementScore(
                                    finalMap,
                                    division,
                                    riskForDivision.get(division)));
        }
    }

    // Evaluator Agreement metrics getter
    public Map<String, Double> getDivisionalWeightedEntropy() {
        return divisionalWeightedEntropy;
    }

    public Map<String, Double> getDivisionalConsensusScore() {
        return divisionalConsensusScore;
    }

    public Double getNationalAverageEntropy() {
        return nationalAverageEntropy;
    }

    public Double getNationalConsensusScore() {
        return nationalConsensusScore;
    }

    public Double getKrippendorff() {
        return krippendorff;
    }


    // Mean measure metrics getter
    public Map<String, Double> getMeanAgreementScoreForDivison() {
        return meanAgreementScoreForDivison;
    }

    public Map<String, Double> getMeanCertaintyForForDivision() {
        return meanCertaintyForForDivision;
    }

    public Map<String, RiskLevel> getDominantPerceivedRiskLevelForDivison() {
        return dominantPerceivedRiskLevelForDivison;
    }


    // Model evaluation metrics getter
    public Map<String, Double> getWeightedDivisionalLevelAgreementScore() {
        return weightedDivisionalLevelAgreementScore;
    }

    public Double getNationalModelFieldAgreementScore() {
        return nationalModelFieldAgreementScore;
    }
}