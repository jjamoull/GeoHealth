package com.webgis.map;

import com.Converter.ZipFiles;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.DeleteMapping;


import org.springframework.web.multipart.MultipartFile;

//import java.io.File;
import java.io.IOException;
import java.util.Optional;

@RestController
@RequestMapping("/maps")
@CrossOrigin(origins = "http://localhost:4200")
public class MapController {
    private final MapService mapService;

    private final ZipFiles unzipper;

    public MapController( MapService mapService){
        this.mapService = mapService;
        this.unzipper = new ZipFiles();
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

        //----------------------------------------------------------------------------------------------
        //temporary disabling the unzipping to avoid polluting the github, files will be deleted after
        // transformation into geoJSON but this aspect is not yet implemented
        
        //File fileToUnzip = new File(zipFile.getOriginalFilename());
        //zipFile.transferTo(fileToUnzip);
        //unzipper.unzip(map, fileToUnzip);
        //----------------------------------------------------------------------------------------------

        return mapService.save(map);
    }


    @PostMapping("/save_geoJsonFile/{id}")
    public Map addGeoJSONFile(@RequestBody Map mapToAdd, @RequestBody byte[] geoJsonFile){
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
