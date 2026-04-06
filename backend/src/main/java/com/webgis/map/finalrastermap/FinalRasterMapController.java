package com.webgis.map.finalrastermap;

import com.webgis.map.finalrastermap.dto.FinalRasterMapDto;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/finalRasterMaps")
public class FinalRasterMapController {
    private final FinalRasterMapService finalRasterMapService;

    public FinalRasterMapController(FinalRasterMapService finalRasterMapService){
        this.finalRasterMapService = finalRasterMapService;
    }

    @GetMapping("/file/{id}")
    public ResponseEntity<FinalRasterMap> getFinalRasterMap(@PathVariable long id) {
        final Optional<FinalRasterMap> finalRasterMap = finalRasterMapService.findById(id);

        if(finalRasterMap.isPresent()){
            return ResponseEntity.status(200).body(finalRasterMap.get());
        }

        return ResponseEntity.status(404).build();
    }

    @GetMapping("/allMaps")
    public ResponseEntity<List<FinalRasterMapDto>> getAllRiskFactorMaps() {
        try {
            final List<FinalRasterMapDto> dtoList = new ArrayList<>();

            final List<FinalRasterMap> finalRasterMapList = finalRasterMapService.findAll();
            for (FinalRasterMap frm : finalRasterMapList) {
                final FinalRasterMapDto dto = new FinalRasterMapDto(frm.getId(), frm.getTitle());
                dtoList.add(dto);
            }

            return ResponseEntity.status(200).body(dtoList);
        } catch (Exception e) {
            return ResponseEntity.status(404).build();
        }
    }
}
