package com.webgis.admin.map;

import com.webgis.map.raster.RasterMap;
import com.webgis.map.raster.RasterMapService;
import com.webgis.map.service.TransformTifFiles;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("admin/rasterMaps")
public class AdminRasterMapController {

    private final RasterMapService rasterMapService;
    private final TransformTifFiles transformTifFiles;

    public AdminRasterMapController(
            RasterMapService riskFactorMapService,
            TransformTifFiles transformTifFiles){
        this.rasterMapService = riskFactorMapService;
        this.transformTifFiles = transformTifFiles;

    }

    @PostMapping(value = "/file", consumes = "multipart/form-data")
    public ResponseEntity<Object> postTifFile(
            @RequestParam("title") String title,
            @RequestParam("description") String description,
            @RequestParam(value="tifFile") MultipartFile tifFile) {

        try {
            final RasterMap rasterMap = new RasterMap(title, description);
            rasterMapService.save(rasterMap);

            transformTifFiles.transformIntoTileFile(rasterMap.getId(), tifFile);

            return ResponseEntity.status(200).body(rasterMapService.save(rasterMap));
        } catch (Exception e) {
            return ResponseEntity.status(404).build();
        }

    }
}
