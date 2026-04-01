package com.webgis.measure;

import com.webgis.MessageDto;
import com.webgis.map.finalmap.FinalMap;
import com.webgis.map.finalmap.FinalMapService;
import com.webgis.measure.dto.DivisionRiskDto;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PostMapping;

import java.io.IOException;
import java.util.Optional;

@RestController
@RequestMapping("/measure")
public class MeasureController {

    private final MeasureService measureService;
    private final FinalMapService finalMapService;

    public MeasureController(MeasureService measureService,
                             FinalMapService finalMapService){
        this.measureService = measureService;
        this.finalMapService=finalMapService;
    }

    /**
     * Get the weighted entropy for a division for a map
     *
     * @param mapId the id of the map you are interested in
     * @param division the name of the division you are interested in
     * @param divisionRisk the riskLevel of the division on the map that you are interested in
     *
     * @return weighted entropy for a division for a map if the map exist, not found otherwise
     */
    @GetMapping("/weightedEntropy/{mapId}/{division}/{divisionRisk}")
    public ResponseEntity<Object> getWeightedEntropyForADivision(
            @PathVariable long mapId,
            @PathVariable String division,
            @PathVariable String divisionRisk){


        final Optional<FinalMap> optionalFinalMap= finalMapService.findById(mapId);

        if(optionalFinalMap.isEmpty()){
            return ResponseEntity.status(404).body(new MessageDto("The selected map does not exist"));
        }

        final FinalMap finalMap= optionalFinalMap.get();

        final double weightedEntropy=measureService.computeWeightedEntropyForADivision(finalMap,division,divisionRisk);

        return ResponseEntity.status(200).body(weightedEntropy);

    }

    /**
     * Get the global consensus index for a map
     *
     * @param mapId the name of the division you are interested in
     * @param divisionRiskDto a Dto containing for each division its risk level
     *
     * @return global consensus index for a map if the map exist, not found otherwise
     */
    @PostMapping("/globalConsensusIndex/{mapId}")
    public ResponseEntity<Object> getGlobalConsensusIndex(
            @PathVariable long mapId,
            @RequestBody DivisionRiskDto divisionRiskDto){

        final Optional<FinalMap> optionalFinalMap= finalMapService.findById(mapId);

        if(optionalFinalMap.isEmpty()){
            return ResponseEntity.status(404).body(new MessageDto("The selected map does not exist"));
        }

        final FinalMap finalMap= optionalFinalMap.get();

        final double golbalConsensusIndex=measureService.computeGlobalConsensusIndex(
                finalMap,
                divisionRiskDto.divisionRiskLevel());

        return ResponseEntity.status(200).body(golbalConsensusIndex);
    }

    @GetMapping("/krippensdorffAplha/{mapId}")
    public ResponseEntity<Object> getKrippensdorffAplha(
            @PathVariable long mapId){

        final Optional<FinalMap> optionalFinalMap= finalMapService.findById(mapId);

        if(optionalFinalMap.isEmpty()){
            return ResponseEntity.status(404).body(new MessageDto("The selected map does not exist"));
        }

        final FinalMap finalMap= optionalFinalMap.get();

        try{
            final double krippensdorffAlpha= measureService.computeKrippensdorffAplha(finalMap);
            return ResponseEntity.status(200).body(krippensdorffAlpha);
        } catch (Exception e){
            return ResponseEntity.status(404).body(new MessageDto("Error while computing krippensdorff"));
        }

    }
}
