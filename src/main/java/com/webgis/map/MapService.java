package com.webgis.map;

import org.springframework.stereotype.Service;
import java.util.Optional;

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
}
