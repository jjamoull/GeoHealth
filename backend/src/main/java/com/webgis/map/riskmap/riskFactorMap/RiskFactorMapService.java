package com.webgis.map.riskmap.riskFactorMap;


import com.converter.TiffFiles;
import com.webgis.map.riskmap.tile.tile.Tile;
import com.webgis.map.riskmap.tile.tile.TileId;
import com.webgis.map.riskmap.tile.tile.TileRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

@Service
public class RiskFactorMapService {

    static Logger logger = LoggerFactory.getLogger(RiskFactorMapService.class);

    private final RiskFactorMapRepository riskFactorMapRepository;
    private final TileRepository tileRepository;

    public RiskFactorMapService (RiskFactorMapRepository riskFactorMapRepository,
                                 TileRepository tileRepository ){
        this.riskFactorMapRepository = riskFactorMapRepository;
        this.tileRepository = tileRepository;

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
     * Update the type of param tifFile to be interpretable by the method that is called
     *  after to transform tif into Tiles
     *
     * @param tifFile : the file to transform into Tiles
     * @return the path of the file transformed
     * @throws IOException : if there is an issue to execute method called in return
     * */
    public Path transformTifFile(MultipartFile tifFile)throws IOException{
        try{
            final byte[] byteTifFile = tifFile.getBytes();
            final TiffFiles tifToTransform = new TiffFiles(byteTifFile);
            return tifToTransform.executeTransformationCommand();
        } catch (IOException e) {
            throw new IOException(e);
        }
    }

    /**
     * Main method to transform tif files into tiles and stored them into the DB
     *
     * @param mapId : the id of the risk factor map that the result will be linked
     * @param tifFile : the fil file to transform
     * */
    public void transformIntoTileFile(long mapId, MultipartFile tifFile){
        try {
            final Path tiles = transformTifFile(tifFile);

            try (Stream<Path> stream = Files.walk(tiles)) {
                final List<Path> tileFiles = stream.filter(Files::isRegularFile)
                        .filter(p -> p.toString().endsWith(".png"))
                        .toList();

                tileFiles.forEach(path -> {
                    try {
                        final byte[] data = Files.readAllBytes(path);

                        final int y = Integer.parseInt(path.getFileName().toString().replace(".png",""));
                        final int x = Integer.parseInt(path.getParent().getFileName().toString());
                        final int zoom = Integer.parseInt(path.getParent().getParent().getFileName().toString());

                        final TileId tileId = new TileId(mapId, zoom, x, y);
                        final Tile tileToStore = new Tile(tileId, data);

                        tileRepository.save(tileToStore);
                    } catch (IOException e) {
                        logger.info("1) Issue with the uploading\n");
                    }
                });
            }
        } catch (IOException e) {
            logger.info("2) Issue with the uploading\n");
        }
    }


}
