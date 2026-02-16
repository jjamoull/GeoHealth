package com.webgis.map;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Optional;

@RestController
@RequestMapping("/maps")
@CrossOrigin(origins = "http://localhost:4200")
public class MapController {
    private final MapService mapService;

    public MapController( MapService mapService){
        this.mapService = mapService;
    }


    /*@GetMapping("/geoJsonFile/{id}")
    public Map getGeoJsonFile(@PathVariable long id){
        final Optional<Map> mapTemp = mapService.findById(id);
        if (mapTemp.isPresent()){
//            final Map map = mapTemp.get();
//            if (map.getFileGeoJson()!= null){
//                return map;
//            }
            mapTemp.map(ResponseEntity::ok)
                    .orElse(ResponseEntity.notFound().build());
        }
        return (Map) ResponseEntity.notFound();
    }*/

    @GetMapping("/geoJsonFile/{id}")
    public ResponseEntity<Map> getGeoJsonFile(@PathVariable long id) {

        return mapService.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }



    @PostMapping(value = "/uploadShapeFile", consumes = "multipart/form-data" )
    public Map postGeoJsonFile(
            @RequestParam("title") String title,
            @RequestParam("description") String description,
            @RequestParam("zipFile") MultipartFile zipFile,
            @RequestParam(value = "geoJsonFile", required = false) MultipartFile geoJsonFile) throws IOException {

        final Map map = new Map(title,
                description,
                zipFile.getBytes(),
                null);
        if (geoJsonFile != null){
            map.setFileGeoJson(geoJsonFile.getBytes());
        }

        return mapService.save(map);
    }


    @PostMapping("/save_geoJsonFile/{id}")
    public Map addUser(@RequestBody Map mapToAdd, @RequestBody byte[] geoJsonFile){
        final Optional<Map> mapTemp = mapService.findByTitle(mapToAdd.getTitle());

        if (mapTemp.isPresent()) {
            final Map map = mapTemp.get();
            return mapService.save(map);
        }
        return null;
    }


    @DeleteMapping("/delete_map")
    public void deleteUser(@RequestBody Map map){
        mapService.deleteMap(map.getId().intValue());
    }





}
