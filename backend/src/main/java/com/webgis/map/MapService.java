package com.webgis.map;

import com.Converter.ZipFiles;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.Optional;

import static com.Converter.DetectFiles.findShpFile;
import static com.Converter.ShapeFileToGeoJsonFile.transformShapeFileToGeoJsonFile;

@Service
public class MapService {

    private final MapRepository mapRepository;
    private final ZipFiles unzipper;

    public MapService(MapRepository mapRepository){
        this.mapRepository = mapRepository;
        this.unzipper = new ZipFiles();
    }

    /**
     * Search for a map in db using its identifier
     *
     * @param id identifier of the map you want to retrieve from the db
     * @return Map which identifier equals to id, empty otherwise
     */
    public Optional<Map> findById(long id){
        return mapRepository.findById(id);
    }

    /**
     * Returns a map from the database, find it by searching for specified name
     *
     * @param title name of the map you want to retrieve from the db
     * @return Map found using its name, empty if there's none
     * */
    public Optional<Map> findByTitle(String title){return mapRepository.findByTitle(title);}


    /**
     * Save the geoJSON file and the files used to create it for a map identified by its id
     *
     * @param map : the map you want to add geoJsonFile from the db
     * @return Saved map
     * */
    public Map save(Map map){
        return mapRepository.save(map);
    }

    /**
     * Delete the map which identifier equals id
     *
     * @param id The id of the map you want to delete
     */
    public void deleteMap(long id){
        final Optional<Map> map = findById(id);
        if (map.isEmpty()) {
            throw new IllegalArgumentException("Map does not exist");
        }
        final Map mapDel = map.get();
        mapRepository.delete(mapDel);
    }

    /**
     * Converts a zipFile containing shapefiles into a geoJSON file
     *
     * @param id : id of the map in the database
     * @throws IOException : if method findShpFile doesn't find a shp file
     */
    public String toGeoJsonFile(long id) throws IOException{
        Map map = mapRepository.findById(id).orElseThrow(()-> new RuntimeException("The map is not found for this id :"+id));
        File tempFile = Files.createTempDirectory("shp_").toFile();

        unzipper.unzip(map, tempFile);

        // shp file that will be converted and used to detect others important files into zip file for geojson file
        File shpFile = findShpFile(tempFile);

        return transformShapeFileToGeoJsonFile(shpFile);
    }


}
