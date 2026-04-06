package com.webgis.admin.map;

import com.webgis.map.riskmap.riskfactormap.RiskFactorMap;
import com.webgis.map.riskmap.riskfactormap.RiskFactorMapService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("admin/riskFactorMaps")
public class AdminFinalRasterMapController {
    
    private final RiskFactorMapService riskFactorMapService;

    public AdminFinalRasterMapController (RiskFactorMapService riskFactorMapService){
        this.riskFactorMapService = riskFactorMapService;
    }

    @PostMapping(value = "/file", consumes = "multipart/form-data")
    public ResponseEntity<Object> postTifFile(
            @RequestParam("title") String title,
            @RequestParam("description") String description,
            @RequestParam(value="tifFile") MultipartFile tifFile) {

        try {
            final RiskFactorMap riskFactorMap = new RiskFactorMap(title, description);
            riskFactorMapService.save(riskFactorMap);

            riskFactorMapService.transformIntoTileFile(riskFactorMap.getId(), tifFile);

            return ResponseEntity.status(200).body(riskFactorMapService.save(riskFactorMap));
        } catch (Exception e) {
            return ResponseEntity.status(404).build();
        }

    }
}
