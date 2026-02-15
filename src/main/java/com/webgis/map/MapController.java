package com.webgis.map;

import com.webgis.user.User;
import com.webgis.user.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/maps")
@CrossOrigin(origins = "http://localhost:4200")
public class MapController {
    private final MapService mapService;

    public MapController( MapService mapService){
        this.mapService = mapService;
    }


    @GetMapping("/geoJsonFile/{id}")
    public Map getGeoJsonFile(@PathVariable long id){
        final Optional<Map> mapTemp = mapService.findById(id);
        if (mapTemp.isPresent()){
            final Map map = mapTemp.get();
            if (map.getFileGeoJson()!= null){
                return map;
            }
        }
        return (Map) ResponseEntity.notFound();
    }


    @PostMapping(value = "/uploadShapeFile", consumes = "multipart/form-data" )
    public Map postGeoJsonFile(
            @RequestParam("name") String name,
            @RequestParam("zipFile") MultipartFile zipFile,
            @RequestParam(value = "geoJsonFile", required = false) MultipartFile geoJsonFile) throws IOException {

        Map map = new Map(name,
                zipFile.getBytes(),
                null);
        if (geoJsonFile != null){
            map.setFileGeoJson(geoJsonFile.getBytes());
        }

        return mapService.save(map);
    }


    @PostMapping("/save_geoJsonFile/{id}")
    public Map addUser(@RequestBody Map mapToAdd, @RequestBody byte[] geoJsonFile){
        final Optional<Map> mapTemp = mapService.findByName(mapToAdd.getName());

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
