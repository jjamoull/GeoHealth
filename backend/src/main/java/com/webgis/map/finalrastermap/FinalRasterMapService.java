package com.webgis.map.finalrastermap;


import com.webgis.map.riskmap.tile.TileService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class FinalRasterMapService {


    private final FinalRasterMapRepository finalRasterMapRepository;
    private final TileService tileService;


    public FinalRasterMapService (FinalRasterMapRepository finalRasterMapRepository,
                                 TileService tileService ){
        this.finalRasterMapRepository = finalRasterMapRepository;
        this.tileService= tileService;
    }


    /**
     * Search for a final raster map in db using its identifier
     *
     * @param id identifier of the risk factor you want to retrieve from the db
     * @return risk factor which identifier equals to id, empty otherwise
     */
    public Optional<FinalRasterMap> findById(long id){
        return finalRasterMapRepository.findById(id);
    }



    /**
     * Save the geoJSON file and the files used to create it for a final raster map identified by its id
     *
     * @param finalRasterMap : the final raster map you want to add geoJsonFile from the db
     * @return Saved map
     *
     */
    public FinalRasterMap save(FinalRasterMap finalRasterMap){
        return finalRasterMapRepository.save(finalRasterMap);
    }



    /**
     * Gets all risk factor map instances
     *
     * @return list of all the risk factor maps contained in database
     * */
    public List<FinalRasterMap> findAll(){
        return this.finalRasterMapRepository.findAll();
    }

}
