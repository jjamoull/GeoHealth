package com.webgis.map.raster;



import com.webgis.map.raster.dto.RasterMapListDto;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;



import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/rasterMaps")
public class RasterMapController {
    private final RasterMapService rasterMapService;

    public RasterMapController(RasterMapService rasterMapService){
        this.rasterMapService = rasterMapService;
    }

    @GetMapping("/file/{id}")
    public ResponseEntity<RasterMap> getRiskFactorMap(@PathVariable long id) {
        final Optional<RasterMap> riskFactorMap = rasterMapService.findById(id);

        if(riskFactorMap.isPresent()){
            return ResponseEntity.status(200).body(riskFactorMap.get());
        }

        return ResponseEntity.status(404).build();
    }

    @GetMapping("/rasters")
    public ResponseEntity<List<RasterMapListDto>> getAllRasterMaps() {
        try {
            final List<RasterMapListDto> dtoList = new ArrayList<>();

            final List<RasterMap> riskFactorMapList = rasterMapService.findRasters();
            for (RasterMap rf : riskFactorMapList) {
                final RasterMapListDto dto = new RasterMapListDto(rf.getId(), rf.getTitle());
                dtoList.add(dto);
            }

            return ResponseEntity.status(200).body(dtoList);
        } catch (Exception e) {
            return ResponseEntity.status(404).build();
        }
    }

    @GetMapping("/riskFactors")
    public ResponseEntity<List<RasterMapListDto>> getRiskFactors() {
        try {
            final List<RasterMapListDto> dtoList = new ArrayList<>();

            final List<RasterMap> riskFactorList = rasterMapService.findRiskFactors();
            for (RasterMap rasterMap : riskFactorList) {
                final RasterMapListDto dto = new RasterMapListDto(rasterMap.getId(), rasterMap.getTitle());
                dtoList.add(dto);
            }

            return ResponseEntity.status(200).body(dtoList);
        } catch (Exception e) {
            return ResponseEntity.status(404).build();
        }
    }


}