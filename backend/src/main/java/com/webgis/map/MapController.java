package com.webgis.map;

import com.webgis.MessageDto;
import com.webgis.map.dto.MapDto;
import com.webgis.map.dto.MapListDto;
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
@RequestMapping("/maps")
public class MapController {
    private final MapService mapService;

    public MapController( MapService mapService){
        this.mapService = mapService;
    }

    @GetMapping("/{id}")
    public ResponseEntity<Object> getGeoJsonFile(@PathVariable long id) {
        try {
            final Optional<Map> optionalMap = mapService.findById(id);
            if (optionalMap.isPresent()) {
                final Map map = optionalMap.get();
                final MapDto mapDto = new MapDto(map.getId(), map.getTitle(), map.getDescription(), map.getFileGeoJson());
                return ResponseEntity.status(200).body(mapDto);
            }
            return ResponseEntity.status(404).body(new MessageDto("Map not found"));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(400).body(new MessageDto(e.getMessage()));
        }
    }

    @GetMapping("/AllMaps")
    public ResponseEntity<Object> getAllMaps() {
        try {
            final List<Map> allMaps = mapService.findAll();
            final List<MapListDto> mapListDtoList = new ArrayList<>();
            for(Map map : allMaps){
                mapListDtoList.add(new MapListDto(map.getId(), map.getTitle(), map.getDescription()));
            }
            return ResponseEntity.status(200).body(mapListDtoList);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(400).body(new MessageDto(e.getMessage()));
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Object> deleteMap(@PathVariable long id) {
        try {
            mapService.deleteMap(id);
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
            @RequestParam(value = "geoJsonFile", required = false) MultipartFile geoJsonFile) throws IOException {

        try {
            final Map map = new Map(title,
                    description,
                    zipFile.getBytes(),
                    null);
            mapService.save(map);

            if (geoJsonFile != null){
                map.setFileGeoJson(new String(geoJsonFile.getBytes()));
            } else{
                if (map.getId()== null){
                    throw new RuntimeException("There is no id for the map : "+ title);
                } else {
                    final String tempGeoJsonFile = mapService.zipToGeoJsonFile(map.getId());
                    map.setFileGeoJson(tempGeoJsonFile);
                }
            }
            final Map savedMap = mapService.save(map);
            return ResponseEntity.status(200).body(new MapListDto(savedMap.getId(), savedMap.getTitle(), savedMap.getDescription()));
        } catch (RuntimeException e) {
            return ResponseEntity.status(400).body(new MessageDto(e.getMessage()));
        } catch (IOException e) {
            return ResponseEntity.status(500).body(new MessageDto(e.getMessage()));
        }
    }

}
