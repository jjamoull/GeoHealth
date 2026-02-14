package com.webgis.map;

import com.webgis.user.User;
import com.webgis.user.UserService;
import org.springframework.web.bind.annotation.*;

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
        return null;
    }


    @PostMapping("/geoJsonFile")
    public Map postGeoJsonFile(@RequestBody String name,
                               @RequestBody byte[] fileShp,
                               @RequestBody byte[] fileShx,
                               @RequestBody byte[] filePrj,
                               @RequestBody byte[] fileDbf,
                               @RequestBody byte[] fileCpg,
                               @RequestBody byte[] geoJsonFile){
        return mapService.save(
                name,
                fileShp,
                fileShx,
                filePrj,
                fileDbf,
                fileCpg,
                geoJsonFile
        );
    }


    @PostMapping("/save_geoJsonFile/{id}")
    public Map addUser(@RequestBody Map mapToAdd, @RequestBody byte[] geoJsonFile){
        final Optional<Map> mapTemp = mapService.findByName(mapToAdd.getName());

        if (mapTemp.isPresent()) {
            final Map map = mapTemp.get();
            return mapService.save(
                    map.getName(),
                    map.getFileShx(),
                    map.getFileDbf(),
                    map.getFileShp(),
                    map.getFileCpg(),
                    map.getFilePrj(),
                    geoJsonFile
            );
        }
        return null;
    }


    @DeleteMapping("/delete_map")
    public void deleteUser(@RequestBody Map map){
        mapService.deleteMap(map.getId().intValue());
    }





}
