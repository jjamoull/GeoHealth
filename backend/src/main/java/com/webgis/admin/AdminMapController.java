package com.webgis.admin;

import com.webgis.MessageDto;
import com.webgis.map.Map;
import com.webgis.map.MapService;
import com.webgis.map.dto.MapListDto;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@RequestMapping("/admin/maps")
public class AdminMapController {

    private final MapService mapService;

    public AdminMapController(MapService mapService){
        this.mapService = mapService;
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

            System.out.println("in postGeoJsonFile");

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

