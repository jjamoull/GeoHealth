package com.webgis.measure.measurecontroller;

import com.webgis.MessageDto;
import com.webgis.map.finalmap.FinalMap;
import com.webgis.map.finalmap.FinalMapService;
import com.webgis.measure.dto.DivisionRiskDto;
import com.webgis.measure.measureservice.ModelEvaluationMeasureService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@RestController
@RequestMapping("/modelEvaluationMeasure")
public class ModelEvaluationMeasureController {

    private final ModelEvaluationMeasureService modelEvaluationMeasureService;
    private final FinalMapService finalMapService;

    public ModelEvaluationMeasureController (
            ModelEvaluationMeasureService modelEvaluationMeasureService ,
            FinalMapService finalMapService){

        this.modelEvaluationMeasureService=modelEvaluationMeasureService;
        this.finalMapService=finalMapService;
    }

    @GetMapping("/weightedDivisionalLevelAgreementScore/{mapId}/{division}/{divisionRisk}")
    public ResponseEntity<Object> getWeightedDivisionalLevelAgreementScore(
            @PathVariable long mapId,
            @PathVariable String division,
            @PathVariable String divisionRisk){


        final Optional<FinalMap> optionalFinalMap= finalMapService.findById(mapId);

        if(optionalFinalMap.isEmpty()){
            return ResponseEntity.status(404).body(new MessageDto("The selected map does not exist"));
        }

        final FinalMap finalMap= optionalFinalMap.get();

        final double weightedDivisionalLevelAgreementScore=modelEvaluationMeasureService
                .computeWeightedDivisionalLevelAgreementScore(finalMap,division,divisionRisk);

        return ResponseEntity.status(200).body(weightedDivisionalLevelAgreementScore);
    }


    @PostMapping("/nationalModelFieldAgreementScore/{mapId}")
    public ResponseEntity<Object> getNationalModelFieldAgreementScore(
            @PathVariable long mapId,
            @RequestBody DivisionRiskDto divisionRiskDto){

        final Optional<FinalMap> optionalFinalMap= finalMapService.findById(mapId);

        if(optionalFinalMap.isEmpty()){
            return ResponseEntity.status(404).body(new MessageDto("The selected map does not exist"));
        }

        final FinalMap finalMap= optionalFinalMap.get();

        final double nationalModelFieldAgreementScore=modelEvaluationMeasureService
                .computeNationalModelFieldAgreementScore(finalMap,divisionRiskDto.divisionRiskLevel());

        return ResponseEntity.status(200).body(nationalModelFieldAgreementScore);
    }
}
