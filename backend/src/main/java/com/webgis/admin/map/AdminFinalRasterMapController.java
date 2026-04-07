package com.webgis.admin.map;

import com.webgis.map.finalrastermap.FinalRasterMap;
import com.webgis.map.finalrastermap.FinalRasterMapService;
import com.webgis.map.riskmap.riskfactormap.RiskFactorMapService;
import com.webgis.map.service.TransformTifFiles;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;


@RestController
@RequestMapping("admin/finalRasterMaps")
public class AdminFinalRasterMapController {

    private final FinalRasterMapService finalRasterMapService;
    private final RiskFactorMapService riskFactorMapService;
    private final TransformTifFiles transformTifFiles;

    public AdminFinalRasterMapController (
            FinalRasterMapService finalRasterMapService,
            RiskFactorMapService riskFactorMapService,
            TransformTifFiles transformTifFiles){
        this.finalRasterMapService = finalRasterMapService;
        this.riskFactorMapService = riskFactorMapService;
        this.transformTifFiles = transformTifFiles;
    }

    @PostMapping(value = "/file", consumes = "multipart/form-data")
    public ResponseEntity<Object> postTifFile(
            @RequestParam("title") String title,
            @RequestParam("description") String description,
            @RequestParam(value="tifFile") MultipartFile tifFile) {

        try {
            final FinalRasterMap finalRasterMap = new FinalRasterMap(title, description);
            finalRasterMapService.save(finalRasterMap);

            transformTifFiles.transformIntoTileFile(finalRasterMap.getId(), tifFile);

            return ResponseEntity.status(200).body(finalRasterMapService.save(finalRasterMap));
        } catch (Exception e) {
            return ResponseEntity.status(404).build();
        }
    }

}
