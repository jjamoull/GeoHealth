package com.webgis.measure.measurecontroller;


import com.webgis.MessageDto;
import com.webgis.map.finalmap.FinalMap;
import com.webgis.map.finalmap.FinalMapService;
import com.webgis.measure.measureservice.EvaluatorAgreementMeasureService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@RestController
@RequestMapping("/evaluatorAgreementMeasure")
public class EvaluatorAgreementMeasureController {

    private final EvaluatorAgreementMeasureService evaluatorAgreementMeasureService;
    private final FinalMapService finalMapService;

    public EvaluatorAgreementMeasureController(
            EvaluatorAgreementMeasureService evaluatorAgreementMeasureService,
            FinalMapService finalMapService){

        this.evaluatorAgreementMeasureService=evaluatorAgreementMeasureService;
        this.finalMapService=finalMapService;
    }

    /**
     * Get the divisional consensus score for a specific division of a map
     *
     * @param mapId the id of the map you are interested
     * @param division the division you are interested in
     *
     * @return the divisionalConsensusScore if the map exists, not found otherwise
     */
    @GetMapping("/divisionalConsensusScore/{mapId}/{division}")
    public ResponseEntity<Object> getDivisionalConsensusScore(
            @PathVariable long mapId,
            @PathVariable String division){

        final Optional<FinalMap> optionalFinalMap= finalMapService.findById(mapId);

        if(optionalFinalMap.isEmpty()){
            return ResponseEntity.status(404).body(new MessageDto("The selected map does not exist"));
        }

        final FinalMap finalMap= optionalFinalMap.get();

        final double divisionalConsensusScore =evaluatorAgreementMeasureService
                .computeDivisionalConsensusScore(finalMap,division);

        return ResponseEntity.status(200).body(divisionalConsensusScore);
    }

    /**
     * Get the national consensus score for a map
     *
     * @param mapId the id of the map you are interested
     *
     * @return the national consensus score if map exists, not found otherwise
     */
    @GetMapping("/nationalConsensusScore/{mapId}/{division}")
    public ResponseEntity<Object> getNationalConsensusScore(
            @PathVariable long mapId){

        final Optional<FinalMap> optionalFinalMap= finalMapService.findById(mapId);

        if(optionalFinalMap.isEmpty()){
            return ResponseEntity.status(404).body(new MessageDto("The selected map does not exist"));
        }

        final FinalMap finalMap= optionalFinalMap.get();

        final double NationalConsensusScore =evaluatorAgreementMeasureService
                .computeNationalConsensusScore(finalMap);

        return ResponseEntity.status(200).body(NationalConsensusScore);
    }

    /**
     * Get the krippendorff alpha score for a map
     *
     * @param mapId the id of the map you are interested
     *
     * @return krippendorff alpha score if the map exists, not found otherwise
     */
    @GetMapping("/krippendorffAlpha/{mapId}")
    public ResponseEntity<Object> getkrippendorffAlpha(
            @PathVariable long mapId){

        final Optional<FinalMap> optionalFinalMap= finalMapService.findById(mapId);

        if(optionalFinalMap.isEmpty()){
            return ResponseEntity.status(404).body(new MessageDto("The selected map does not exist"));
        }

        final FinalMap finalMap= optionalFinalMap.get();

        try{
            final double krippendorffAlpha= evaluatorAgreementMeasureService
                    .computekrippendorffAlpha(finalMap);
            return ResponseEntity.status(200).body(krippendorffAlpha);
        } catch (Exception e){
            return ResponseEntity.status(404).body(new MessageDto("Error while computing krippensdorff"));
        }

    }
}
