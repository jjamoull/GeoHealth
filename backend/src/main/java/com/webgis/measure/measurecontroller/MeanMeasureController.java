package com.webgis.measure.measurecontroller;

import com.webgis.MessageDto;
import com.webgis.map.finalmap.FinalMap;
import com.webgis.map.finalmap.FinalMapService;
import com.webgis.measure.measureservice.MeanMesureService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@RestController
@RequestMapping("/meanMeasure")
public class MeanMeasureController {

    private final MeanMesureService meanMesureService;
    private final FinalMapService finalMapService;

    public MeanMeasureController( MeanMesureService meanMesureService,
                              FinalMapService finalMapService){
        this.meanMesureService=meanMesureService;
        this.finalMapService=finalMapService;
    }

    /**
     * Get the mean of AgreementScore for a specific division of a map
     *
     * @param mapId the id of the map you are interested
     * @param division the division you are interested in
     *
     * @return mean of AgreementScore for a specific division of a map if the map exists, not found otherwise
     */
    @GetMapping("/meanDivisionalAgreementScore/{mapId}/{division}")
    public ResponseEntity<Object> getMeanDivisionalAgreementScore(
            @PathVariable long mapId,
            @PathVariable String division){

        final Optional<FinalMap> optionalFinalMap= finalMapService.findById(mapId);

        if(optionalFinalMap.isEmpty()){
            return ResponseEntity.status(404).body(new MessageDto("The selected map does not exist"));
        }

        final FinalMap finalMap= optionalFinalMap.get();

        final Double meanDivisionalAgreementScore =meanMesureService
                .computeMeanDivisionalAgreementScore(finalMap,division);

        return ResponseEntity.status(200).body(meanDivisionalAgreementScore);
    }

    /**
     * Get the mean of certainty score for a specific division of a map
     *
     * @param mapId the id of the map you are interested
     * @param division the division you are interested in
     *
     * @return mean of certainty score for a specific division of a map if the map exists, not found otherwise
     */
    @GetMapping("/meanCertainty/{mapId}/{division}")
    public ResponseEntity<Object> getMeanCertaintyForMapForDivision(
            @PathVariable long mapId,
            @PathVariable String division){

        final Optional<FinalMap> optionalFinalMap= finalMapService.findById(mapId);

        if(optionalFinalMap.isEmpty()){
            return ResponseEntity.status(404).body(new MessageDto("The selected map does not exist"));
        }

        final FinalMap finalMap= optionalFinalMap.get();

        final Double meanCertainty=meanMesureService
                .computeMeanCertaintyForMapForDivision(finalMap,division);

        return ResponseEntity.status(200).body(meanCertainty);
    }

    /**
     * Get the dominant perceived riskLevel for a specific division of a map
     *
     * @param mapId the id of the map you are interested
     * @param division the division you are interested in
     *
     * @return the dominant perceived riskLevel for a specific division of a map if the map exists, not found otherwise
     */
    @GetMapping("/dominantPerceivedRiskLevel/{mapId}/{division}")
    public ResponseEntity<Object> getDominantPerceivedRiskLevelForMapForDivision(
            @PathVariable long mapId,
            @PathVariable String division){

        final Optional<FinalMap> optionalFinalMap= finalMapService.findById(mapId);

        if(optionalFinalMap.isEmpty()){
            return ResponseEntity.status(404).body(new MessageDto("The selected map does not exist"));
        }

        final FinalMap finalMap= optionalFinalMap.get();

        final String dominantPerceivedRisk=meanMesureService
                .computeDominantPerceivedRiskLevelForMapForDivision(finalMap,division).toString();

        return ResponseEntity.status(200).body(dominantPerceivedRisk);
    }

}
