package com.webgis.map.finalmap;

import com.converter.ZipFiles;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.List;
import java.util.Optional;

import static com.converter.DetectFiles.findShpFile;
import static com.converter.ShapeFileToGeoJsonFile.transformShapeFileToGeoJsonFile;

@Service
public class FinalMapService {

    private final FinalMapRepository finalMapRepository;
    private final ZipFiles unzipper;

    public FinalMapService(FinalMapRepository finalMapRepository){
        this.finalMapRepository = finalMapRepository;
        this.unzipper = new ZipFiles();
    }

    /**
     * Search for a map in db using its identifier
     *
     * @param id identifier of the map you want to retrieve from the db
     * @return Map which identifier equals to id, empty otherwise
     */
    public Optional<FinalMap> findById(long id){
        return finalMapRepository.findById(id);
    }

    /**
     * Returns a map from the database, find it by searching for specified name
     *
     * @param title name of the map you want to retrieve from the db
     * @return Map found using its name, empty if there's none
     * */
    public Optional<FinalMap> findByTitle(String title){return finalMapRepository.findByTitle(title);}


    /**
     * Save the geoJSON file and the files used to create it for a map identified by its id
     *
     * @param finalMap : the map you want to add geoJsonFile from the db
     * @return Saved map
     * */
    public FinalMap save(FinalMap finalMap){
        return finalMapRepository.save(finalMap);
    }

    /**
     * Delete the map which identifier equals id
     *
     * @param id The id of the map you want to delete
     */
    public void deleteMap(long id){
        final Optional<FinalMap> map = findById(id);
        if (map.isEmpty()) {
            throw new IllegalArgumentException("Map does not exist");
        }
        final FinalMap finalMapDel = map.get();
        finalMapRepository.delete(finalMapDel);
    }

    /**
     * Converts a zipFile containing shapefiles into a geoJSON file
     *
     * @param id : id of the map in the database
     * @throws IOException : if method findShpFile doesn't find a shp file
     */
    public String zipToGeoJsonFile(long id) throws IOException{
        final FinalMap finalMap = finalMapRepository.findById(id)
                .orElseThrow(()-> new RuntimeException("The map is not found for this id :"+id));
        final File tempFile = Files.createTempDirectory("shp_").toFile();

        unzipper.unzip(finalMap, tempFile);

        // shp file that will be converted and used to detect others important files into zip file for geojson file
        final File shpFile = findShpFile(tempFile);

        return transformShapeFileToGeoJsonFile(shpFile);
    }

    /**
     * Returns all the maps from the database
     *
     * @returns a list of all the maps
     */
    public List<FinalMap> findAll(){
        return finalMapRepository.findAll();
    }
}
