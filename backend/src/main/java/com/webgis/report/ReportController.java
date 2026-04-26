package com.webgis.report;

import com.webgis.MessageDto;
import com.webgis.map.finalmap.FinalMap;
import com.webgis.map.finalmap.FinalMapService;
import com.webgis.measure.dto.DivisionRiskDto;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PostMapping;


import java.io.IOException;
import java.util.Optional;

@RestController
@RequestMapping("/report")
public class ReportController {

    private final ReportService reportService;
    private final FinalMapService finalMapService;

    public ReportController( ReportService reportService,
                             FinalMapService finalMapService){
        this.reportService=reportService;
        this.finalMapService= finalMapService;
    }

    /**
     * Get a report containing all the metrics for a specific map if the map exists, not found otherwise
     *
     * @param mapId the map you are interested in
     * @param divisionRiskDto A dto containing the risk evaluated in the model(original map) for each division
     *
     * @return the .xlsx report for a map in a byte array format if the map exists, not found otherwise
     */
    @PostMapping("/getReport/{mapId}")
    public ResponseEntity<Object> getReport(
            @PathVariable long mapId,
            @RequestBody DivisionRiskDto divisionRiskDto){

        final Optional<FinalMap> optionalFinalMap= finalMapService.findById(mapId);

        if(optionalFinalMap.isEmpty()){
            return ResponseEntity.status(404).body(new MessageDto("The selected map does not exist"));
        }

        final FinalMap finalMap= optionalFinalMap.get();

        try {

            final byte[] excel = reportService.createReportForMap(finalMap, divisionRiskDto.divisionRiskLevel());

            return ResponseEntity.status(200)
                    .header("Content-Disposition", "attachment; filename=report.xlsx")
                    .header("Content-Type", "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet")
                    .body(excel);
        }
        catch (RuntimeException | IOException e){
            return ResponseEntity.status(500).body(new MessageDto("Fail to create report "));
        }
    }
}
