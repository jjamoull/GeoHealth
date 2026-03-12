package com.webgis.map.finalmap;

import com.webgis.MessageDto;
import com.webgis.exception.NotFound;
import com.webgis.map.finalmap.dto.FinalMapDto;
import com.webgis.map.finalmap.dto.FinalMapListDto;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.DeleteMapping;

import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/finalMaps")
public class FinalMapController {
    private final FinalMapService finalMapService;

    public FinalMapController(FinalMapService finalMapService){
        this.finalMapService = finalMapService;
    }

    @GetMapping("/{id}")
    public ResponseEntity<Object> getGeoJsonFile(@PathVariable long id) {
        try {
            final Optional<FinalMap> optionalMap = finalMapService.findById(id);
            if (optionalMap.isPresent()) {
                final FinalMap finalMap = optionalMap.get();
                final FinalMapDto finalMapDto = new FinalMapDto(
                        finalMap.getId(),
                        finalMap.getTitle(),
                        finalMap.getDescription(),
                        finalMap.getFileGeoJson());
                return ResponseEntity.status(200).body(finalMapDto);
            }
            return ResponseEntity.status(404).body(new MessageDto("Map not found"));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(400).body(new MessageDto(e.getMessage()));
        }
    }

    @GetMapping("/allMaps")
    public ResponseEntity<Object> getAllMaps() {
        try {
            final List<FinalMap> allFinalMaps = finalMapService.findAll();
            final List<FinalMapListDto> finalMapListDtoList = new ArrayList<>();
            for(FinalMap finalMap : allFinalMaps){
                finalMapListDtoList.add(new FinalMapListDto(finalMap.getId(), finalMap.getTitle(), finalMap.getDescription()));
            }
            return ResponseEntity.status(200).body(finalMapListDtoList);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(400).body(new MessageDto(e.getMessage()));
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Object> deleteMap(@PathVariable long id) {
        try {
            finalMapService.deleteMap(id);
            return ResponseEntity.status(200).body(new MessageDto("Map deleted successfully"));
        } catch(IllegalArgumentException e) {
            return ResponseEntity.status(404).body(new MessageDto(e.getMessage()));
        }
    }


    @PostMapping(value = "/uploadShapeFile", consumes = "multipart/form-data" )
    public ResponseEntity<Object> postGeoJsonFile(
            @RequestParam("title") String title,
            @RequestParam("description") String description,
            @RequestParam("zipFile") MultipartFile zipFile,
            @RequestParam(value = "geoJsonFile", required = false) MultipartFile geoJsonFile){

        try {
            final FinalMap finalMap = new FinalMap(title,
                    description,
                    zipFile.getBytes(),
                    null);
            finalMapService.save(finalMap);

            if (geoJsonFile != null){
                finalMap.setFileGeoJson(new String(geoJsonFile.getBytes()));
            } else{
                if (finalMap.getId()== null){
                    throw new NotFound("There is no id for this map : " + finalMap.getTitle());
                } else {
                    final String tempGeoJsonFile = finalMapService.zipToGeoJsonFile(finalMap.getId());
                    finalMap.setFileGeoJson(tempGeoJsonFile);
                }
            }
            final FinalMap savedFinalMap = finalMapService.save(finalMap);
            return ResponseEntity.status(200).body(
                    new FinalMapListDto(savedFinalMap.getId(),
                            savedFinalMap.getTitle(),
                            savedFinalMap.getDescription()));
        } catch (NotFound e) {
            return ResponseEntity.status(400).body(new MessageDto(e.getMessage()));
        } catch (IOException e) {
            return ResponseEntity.status(500).body(new MessageDto(e.getMessage()));
        }
    }

}
