package com.webgis.map.riskmap.riskfactormap;


import com.converter.TiffFiles;
import com.webgis.map.riskmap.tile.TileService;
import org.jspecify.annotations.NonNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferByte;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;
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

                        byte[] pixels = decompressPNGFile(path);

                        float[] floatMeans = computeMean(pixels);

                        byte[] means = convertToBytes(floatMeans);


                        tileService.save(mapId, zoom, x, y, data, means);
                    } catch (IOException e) {
                        logger.info("1) Issue with the uploading\n");
                    }
                });
               logger.info("Tile generation and mean computation successfully conducted");
            }
        } catch (IOException e) {
            logger.info("2) Issue with the uploading\n");
        }
    }


    /**
     * compute means of pixels in tile by blocks of size BLOCK_SIZE,
     *
     * @return normalized mean of pixels value (0-1) for each block in a tile
     * */
    private float[] computeMean(byte[] tileData){
        float[] means = new float[TILE_SIZE];
        long sum;
        int count = BLOCK_SIZE*BLOCK_SIZE;

        //for each block
        for (int xOffSet = 0; xOffSet < TILE_SIZE; xOffSet=xOffSet+BLOCK_SIZE){
            for (int yOffSet = 0; yOffSet < TILE_SIZE; yOffSet=yOffSet+BLOCK_SIZE){
                sum = 0;

                //for each pixel in this block
                for (int x = 0; x < BLOCK_SIZE; x++){
                    for (int y = 0; y < BLOCK_SIZE; y++){

                        //0xFF to remove the sign
                        sum += tileData[yOffSet*x + y*TILE_SIZE + xOffSet + x] & 0xFF;
                    }
                }

                means[yOffSet + xOffSet/BLOCK_SIZE] = sum / (float) count*255;
            }
        }
        return means;
    }


    /**
     * Decompress a png file for tile into a TILE_SIZE x TILE_SIZE byte array
     *
     * @param path : path of the file to uncompress
     * @return byte array of size TILE_SIZE x TILE_SIZE
     * @throws RuntimeException
     * */
    private byte[] decompressPNGFile(@NonNull Path path )throws RuntimeException{
        BufferedImage img = null;

        try {
            img = ImageIO.read(path.toFile());
            BufferedImage grayImg = new BufferedImage(
                    img.getWidth(),
                    img.getHeight(),
                    BufferedImage.TYPE_BYTE_GRAY
            );

            Graphics g = grayImg.getGraphics();
            g.drawImage(img, 0, 0, null);
            g.dispose();
            return ((DataBufferByte) grayImg.getRaster().getDataBuffer()).getData();

        } catch (IOException e) {
            logger.error("There is a IOException during the reading of path in decompressPNGFile");
        }

        throw new RuntimeException();
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


    /** returns the index of mean in 1D array of size constant BlOCK_SIZE
     *
     * @param x : int representing the x coordinates of mean BLOCK (0-BLOCK_SIZE)
     * @param y : int representing the y coordinate of mean BLOCK (0-BLOCK_SIZE)
     * @return index for mean of mean in 1D array of size constant BLOCK_SIZE
     * */
    public int getMeanIndex(int x, int y){
        return y*BLOCK_SIZE + x;
    }


    /**returns coordinates of tile in OpenStreetMap 2D space
     *
     * @param z : current zoom level on the map
     * @param lat : latitude
     * @param lng : longitude
     * @return tile coordinates in OpenStreetMap 2D space in format array : [x,y]
     * */
    public int[] getTileCoordinates(int z, double lat, double lng){
        double[] nAndLatRad = getNAndLatRad(z, lat);
        double[] xy = getTileXY(nAndLatRad[0], lng, nAndLatRad[1]);

        return new int[]{
                (int)Math.floor(xy[0]),
                (int)Math.floor(xy[1])
        };
    }


    /**returns coordinates of tile in OpenStreetMap 2D space
     *
     * @param z : current zoom level on the map
     * @param lat : latitude
     * @param lng : longitude
     * @return tile coordinates in OpenStreetMap 2D space in format array : [x,y]
     * */
    public int[] getBlockCoordinates(int z, double lat, double lng){
        double[] nAndLatRad = getNAndLatRad(z, lat);
        double[] xy = getTileXY(nAndLatRad[0], lng, nAndLatRad[1]);

        return new int[]{
                (int)Math.floor((xy[0]*BLOCK_SIZE)%BLOCK_SIZE),
                (int)Math.floor((xy[1]*BLOCK_SIZE)%BLOCK_SIZE)
        };
    }

    /**
     * return 2^zoom and the latitude in radian into an array of size 2
     *
     * @param z : current zoom level on the map
     * @param latitude : latitude
     * @return an array of size 2 with index :
         *  0 : n -> 2^zoom
         *  1 : LatRad -> Latitude in Radian
     * */
    private double[] getNAndLatRad(int z,  double latitude){
        return new double[]{
                (int)Math.pow(2,z),
                (latitude * (Math.PI / 180))
        };
    }


    /**
     * Return X and Y into an array of size 2
     *
     * @param n : represent 2^zoom
     * @param lng : longitude
     * @param lat_rad : Latitude in Radian
     * @return an array of size 2 with index :
     *      0 : X
     *      1 : Y
     **/
    private double[] getTileXY(double n, double lng, double lat_rad){
        return new double[]{
                n*((lng+180)/360),
                 n * ( 1- ( Math.log( Math.tan( lat_rad )  +  1/Math.cos(lat_rad)) / Math.PI))/2
        };
    }



}
