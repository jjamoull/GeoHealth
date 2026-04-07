package com.webgis.map.riskmap.riskfactormap;


import com.converter.TiffFiles;
import com.webgis.map.riskmap.tile.TileService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;


import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

@Service
public class RiskFactorMapService {

    static Logger logger = LoggerFactory.getLogger(RiskFactorMapService.class);

    private final RiskFactorMapRepository riskFactorMapRepository;
    private final TileService tileService;


    public RiskFactorMapService (RiskFactorMapRepository riskFactorMapRepository,
                                 TileService tileService ){
        this.riskFactorMapRepository = riskFactorMapRepository;
        this.tileService= tileService;
    }


    /**
     * Search for a risk factor in db using its identifier
     *
     * @param id identifier of the risk factor you want to retrieve from the db
     * @return risk factor which identifier equals to id, empty otherwise
     */
    public Optional<RiskFactorMap> findById(long id){
        return riskFactorMapRepository.findById(id);
    }

    /**
     * Save the geoJSON file and the files used to create it for a risk factor identified by its id
     *
     * @param riskFactorMap : the risk factor you want to add geoJsonFile from the db
     * @return Saved map
     *
     */
    public RiskFactorMap save(RiskFactorMap riskFactorMap){
        return riskFactorMapRepository.save(riskFactorMap);
    }


    /**
     * Gets all risk factor map instances
     *
     * @return list of all the risk factor maps contained in database
     * */
    public List<RiskFactorMap> findAll(){
        return this.riskFactorMapRepository.findAll();
    }

}
