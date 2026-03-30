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

    @GetMapping("/globalConsensusIndex/{mapId}")
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

    @GetMapping("/kippensdroffAplha/{mapId}")
    public void getKippensdroffAplha(
            @PathVariable long mapId){
             // TODO
    }
}
