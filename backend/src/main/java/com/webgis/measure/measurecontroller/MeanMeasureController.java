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

    @GetMapping("/meanDivisionalAgreementScore/{mapId}/{division}")
    public ResponseEntity<Object> getMeanDivisionalAgreementScore(
            @PathVariable long mapId,
            @PathVariable String division){

        final Optional<FinalMap> optionalFinalMap= finalMapService.findById(mapId);

        if(optionalFinalMap.isEmpty()){
            return ResponseEntity.status(404).body(new MessageDto("The selected map does not exist"));
        }

        final FinalMap finalMap= optionalFinalMap.get();

        final double meanDivisionalAgreementScore =meanMesureService
                .computeMeanDivisionalAgreementScore(finalMap,division);

        return ResponseEntity.status(200).body(meanDivisionalAgreementScore);
    }

    @GetMapping("/MeanCertainty/{mapId}/{division}")
    public ResponseEntity<Object> getMeanCertaintyForMapForDivision(
            @PathVariable long mapId,
            @PathVariable String division){

        final Optional<FinalMap> optionalFinalMap= finalMapService.findById(mapId);

        if(optionalFinalMap.isEmpty()){
            return ResponseEntity.status(404).body(new MessageDto("The selected map does not exist"));
        }

        final FinalMap finalMap= optionalFinalMap.get();

        final double meanCertainty=meanMesureService
                .computeMeanCertaintyForMapForDivision(finalMap,division);

        return ResponseEntity.status(200).body(meanCertainty);
    }

    @GetMapping("/dominantPerceivedRiskLevel/{mapId}/{division}")
    public ResponseEntity<Object> dominantPerceivedRiskLevelForMapForDivision(
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
