package com.webgis.map.riskmap.riskfactormap;



import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;


import org.springframework.web.multipart.MultipartFile;



import java.util.Optional;

@RestController
@RequestMapping("/riskFactor")
public class RiskFactorMapController {
    static Logger logger = LoggerFactory.getLogger(RiskFactorMapController.class);

    private final RiskFactorMapService riskFactorMapService;

    public RiskFactorMapController (RiskFactorMapService riskFactorMapService){
        this.riskFactorMapService = riskFactorMapService;
    }

    @GetMapping("/file/{id}")
    public ResponseEntity<RiskFactorMap> getRiskFactorMap(@PathVariable long id) {
        final Optional<RiskFactorMap> riskFactorMap = riskFactorMapService.findById(id);

        if(riskFactorMap.isPresent()){
            return ResponseEntity.status(200).body(riskFactorMap.get());
        }

        return ResponseEntity.status(404).build();
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