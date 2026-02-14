package com.webgis.map;

import com.webgis.user.User;
import org.springframework.stereotype.Service;

import java.util.Optional;

import static java.lang.System.err;

@Service
public class MapService {

    private final MapRepository mapRepository;

    public MapService(MapRepository mapRepository){
        this.mapRepository = mapRepository;
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
     * @param name name of the map you want to retrieve from the db
     * @return Map found using its name, empty if there's none
     * */
    public Optional<Map> findByName(String name){return mapRepository.findByName(name);}


    /**
     * Save the geoJSON file and the files used to create it for a map identified by its id
     *
     * @param name : name of the map you want to add geoJsonFile from the db
     * @param fileShp : shp file of the map to save
     * @param fileShx : shx file of the map to save
     * @param filePrj : prj file of the map to save
     * @param fileDbf : dbf file of the map to save
     * @param fileCpg : cpg file of the map to save
     * @param geoJsonFile : geoJSON transformation of the map to save
     *
     * @return Saved map
     * */
    public Map save(String name,
                    byte[] fileShp,
                    byte[] fileShx,
                    byte[] filePrj,
                    byte[] fileDbf,
                    byte[] fileCpg,
                    byte[] geoJsonFile){
        final Map map = new Map(name, fileShp, fileShx, filePrj, fileDbf, fileCpg, geoJsonFile);
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
}
