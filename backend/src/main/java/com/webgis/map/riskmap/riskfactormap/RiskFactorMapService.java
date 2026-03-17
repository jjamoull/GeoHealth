package com.webgis.map.riskmap.riskfactormap;


import com.converter.TiffFiles;
import com.webgis.map.riskmap.tile.TileService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

@Service
public class RiskFactorMapService {

    static Logger logger = LoggerFactory.getLogger(RiskFactorMapService.class);

    private final RiskFactorMapRepository riskFactorMapRepository;
    private final TileService tileService;

    final int TILE_SIZE = 256;
    final int BLOCK_SIZE = 16;

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
                        System.out.println("a que coucou 1");
                        float[] floatMeans = computeMean(data);
                        System.out.println("means done");
                        byte[] means = convertToBytes(floatMeans);
                        System.out.println("convert done");

                        tileService.save(mapId, zoom, x, y, data, means);
                    } catch (IOException e) {
                        logger.info("1) Issue with the uploading\n");
                    }
                });
            }
        } catch (IOException e) {
            logger.info("2) Issue with the uploading\n");
        }
    }


    /**
     * compute means of pixels in tile by blocks of size BLOCK_SIZE,
     *
     * @return normalised mean of pixels value (0-1) for each block in a tile
     * */
    private float[] computeMean(byte[] tileData){
        System.out.println("1");
        float[] means = new float[TILE_SIZE];
        long sum;
        int count = BLOCK_SIZE*BLOCK_SIZE;
        System.out.println("1");

        //for each block
        for (int xOffSet = 0; xOffSet < TILE_SIZE; xOffSet=xOffSet+BLOCK_SIZE){
            for (int yOffSet = 0; yOffSet < TILE_SIZE; yOffSet=yOffSet+BLOCK_SIZE){
                sum = 0;
                System.out.println("2");

                //for each pixel in this block
                for (int x = 0; x < BLOCK_SIZE; x++){
                    for (int y = 0; y < BLOCK_SIZE; y++){
                        System.out.println("3");

                        //0xFF to remove the sign
                        sum += tileData[yOffSet*x + y*TILE_SIZE + xOffSet + x] & 0xFF;
                    }
                }
                System.out.println("4");

                means[yOffSet + xOffSet/BLOCK_SIZE] = sum / (float) count*255;
            }
        }
        return means;
    }


    /**
     * convert float array to byte array
     *
     * @return byte array for input
     * */
    private byte[] convertToBytes(float[] floatArray){
        ByteBuffer buffer = ByteBuffer.allocate(TILE_SIZE * 4);
        for (float f : floatArray) {
            buffer.putFloat(f);
        }
        return buffer.array();
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
