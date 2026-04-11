package com.webgis.measure.measureservice;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.webgis.evaluationform.EvaluationForm;
import com.webgis.evaluationform.EvaluationFormRepository;
import com.webgis.map.finalmap.FinalMap;
import com.webgis.measure.RiskLevel;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.EnumMap;
import java.util.List;
import java.util.Map;
import java.util.HashMap;


@Service
public class EvaluatorAgreementMeasureService {

    private final EvaluationFormRepository evaluationFormRepository;

    @Value("${script.path}")
    private String scriptPath;

    public EvaluatorAgreementMeasureService(EvaluationFormRepository evaluationFormRepository) {
        this.evaluationFormRepository = evaluationFormRepository;
    }

    /**
     * Compute the divisional weighted entropy for a specific division of a map
     *
     * @param finalMap the map you are interested in
     * @param division the division you are interested in
     *
     * @return divisional weighted entropy for a specific division of a map
     */
    private double computeDivisionalWeightedEntropy(FinalMap finalMap, String division) {
        final List<EvaluationForm> forms = evaluationFormRepository
                .findByFinalMapAndDivisionAndPerceivedRiskIsNotNullAndCertaintyLevelIsNotNullAndIsPublicTrue(
                        finalMap, division);

        //Compute weight

        final EnumMap<RiskLevel, Double> weightForRiskLevel = new EnumMap<>(RiskLevel.class);
        double totalWeight = 0.0;

        for (EvaluationForm form : forms) {
            final RiskLevel risk = RiskLevel.fromString(form.getPerceivedRisk());
            final double weight = form.getCertaintyLevel() / 4.0;

            weightForRiskLevel.merge(risk, weight, Double::sum);
            totalWeight += weight;
        }

        //Compute entropy

        double divisionalWeightedEntropy = 0.0;

        for (double weightSum : weightForRiskLevel.values()) {
            final double p = weightSum / totalWeight;
            if (p > 0) {
                divisionalWeightedEntropy -= p * Math.log(p); // By default Math.log is ln
            }
        }

        return divisionalWeightedEntropy;
    }

    /**
     * Compute divisional consensus score for a specific division of a map
     * (Measure of disagreement among assessors regarding the perceived risk level.
     *  The higher the consensus score, the greater the agreement between opinions.)
     *
     * @param finalMap the map you are interested in
     * @param division the division you are interested in
     *
     * @return divisional consensus score
     */
    public double computeDivisionalConsensusScore(FinalMap finalMap, String division) {
        final double divisionalWeightedEntropy = computeDivisionalWeightedEntropy(finalMap, division);

        return 1 - (divisionalWeightedEntropy / Math.log(3));
    }

    /**
     * compute the mean of divisional weighted Entropy for a map
     * (Take only into account divisions with at least one valid evaluation)
     *
     * @param finalMap the map you are interested in
     *
     * @return mean of divisional weighted Entropy for a map
     */
    private double computeNationalAverageEntropy(FinalMap finalMap){
        final List<String> divisions = evaluationFormRepository.findDivisionsWithValidPublicEvaluationForms(finalMap);

        if(divisions.isEmpty()){
            return 0.0;
        }

        double sum = 0.0;
        for (String division : divisions) {
            final double divisionalWeightedEntropy = computeDivisionalWeightedEntropy(finalMap, division);
            sum += divisionalWeightedEntropy;
        }

        return sum /divisions.size();
    }

    /**
     * Compute the national consensus score
     * (Measure of disagreement among assessors regarding the perceived risk level on a national level.
     *  The higher the consensus score, the greater the agreement between opinions.)
     *
     * @param finalMap the map you are interested in
     *
     * @return national consensus score
     */
    public double computeNationalConsensusScore(FinalMap finalMap) {

        final double nationalAverageEntropy = computeNationalAverageEntropy(finalMap);

        return 1 - (nationalAverageEntropy / Math.log(3));
    }

    /**
     * Build the matrix needed to compute Krippendorff's alpha measure
     *
     * @param evaluationForms all the evaluation forms of the map you are interested in
     *
     * @return the matrix required to compute the krippendorff measure
     *         (row: evaluator,column: evaluation for a division)
     */
    private List<List<Integer>> buildKrippensdorffMatrix(List<EvaluationForm> evaluationForms){
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

        final Map<Long, Map<String,Integer>> krippendorffHashMap = buildKrippendorffHashMap(evaluationForms);

        final List<List<Integer>> krippensdroffMatrix= new ArrayList<>();
        for(long id: usersIdList){
            final List<Integer> krippensdorffMatrixRow= new ArrayList<>();
            for(String division:evaluatedDivisionsList){
                krippensdorffMatrixRow.add(krippendorffHashMap.get(id).get(division));
            }
            krippensdroffMatrix.add(krippensdorffMatrixRow);
        }
        return krippensdroffMatrix;
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

    /**
     * Compute Kippensdroff's Alpha metrics for a map
     *
     * @param finalMap the map you are interested in
     *
     * @throw IOException if python script not found or failed to open it
     * @return Krippensdroff's Alpha
     */
    public double computekrippendorffAlpha(FinalMap finalMap) throws IOException {
        final List<EvaluationForm> evaluationForms = evaluationFormRepository.findByFinalMap(finalMap);

        final List<List<Integer>> krippensdorffMatrix = buildKrippensdorffMatrix(evaluationForms);

        final ObjectMapper mapper = new ObjectMapper();
        final String json = mapper.writeValueAsString(krippensdorffMatrix);

        final String path= scriptPath+"krippendorff_Alpha.py";

        final ProcessBuilder pb = new ProcessBuilder("python",path);
        final Process p = pb.start();

        final OutputStream os = p.getOutputStream();
        os.write(json.getBytes());
        os.flush();
        os.close();

        final String result = new String(p.getInputStream().readAllBytes());
        return Double.parseDouble(result.trim());
    }

}
