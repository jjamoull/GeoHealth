package com.webgis.map.riskmap.tile;

import com.webgis.map.riskmap.riskfactormap.RiskFactorMap;
import com.webgis.map.riskmap.riskfactormap.RiskFactorMapRepository;
import com.webgis.map.riskmap.riskfactormap.RiskFactorMapService;
import org.jspecify.annotations.NonNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferByte;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.nio.file.Path;
import java.util.Optional;

@Service
public class TileService {
    private final TileRepository tileRepository;
    private final RiskFactorMapRepository riskFactorMapRepository;
    static Logger logger = LoggerFactory.getLogger(TileService.class);
    final int TILE_SIZE = TileConstants.TILE_SIZE;
    final int BLOCK_SIZE = TileConstants.BLOCK_SIZE;
    private static final float MAX_PIXEL_VALUE = 255f;

    public TileService (TileRepository tileRepository, RiskFactorMapRepository riskFactorMapRepository){
        this.tileRepository = tileRepository;
        this.riskFactorMapRepository = riskFactorMapRepository;
    }

    /**
     * saves given tile in the database
     *
     * @param mapId : id of the risk factor map which this tile belongs to
     * @param zoom : zoom level of the tile
     * @param x : x level of the tile
     * @param y : y level of the tile
     * @param data : byte array containing the tile data
     *
     * @return : tile
     * */
    public Tile save(long mapId, int zoom, int x, int y, byte[] data){
        final RiskFactorMap riskFactorMap = riskFactorMapRepository.findById(mapId)
                .orElseThrow(() -> new RuntimeException("Map not found: " + mapId));

        final TileId tileId = new TileId(mapId, zoom, x, y);
        final Tile tileToStore = new Tile(tileId, data, riskFactorMap);

        return tileRepository.save(tileToStore);
    }

    public Optional<Tile> findById(TileId tileId) {
        return tileRepository.findById(tileId);
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
     * @param latRad : Latitude in Radian
     * @return an array of size 2 with index :
     *      0 : X
     *      1 : Y
     **/
    private double[] getTileXY(double n, double lng, double latRad){
        return new double[]{
                n*((lng+180)/360),
                n * ( 1- ( Math.log( Math.tan( latRad )  +  1/Math.cos(latRad)) / Math.PI))/2
        };
    }

    /**
     * compute mean of block at given coordinates in given tileData
     *
     * @param : tileData : byte array containing each pixel value
     * @param : blockCoordinates : coordinates of block in tileData in a TILE_SIZE/BLOCK_SIZE x TILE/SIZE/BLOCK_SIZE 2d space
     *          i.e. block at coordinates (15,15) is the last block of tileData for TILE_SIZE = 16 & BLOCK_SIZE = 16
     * @return mean of pixel values in block
     * */
    public float getTileMeanBlock(byte[] tileData, int[] blockCoordinates){
        long sum=0;
        int count = BLOCK_SIZE*BLOCK_SIZE;

        //getting the x and y offsets for tileData (coordinates of the first pixel of the block)
        int xOffset = blockCoordinates[0]*BLOCK_SIZE;
        int yOffset = blockCoordinates[1]*BLOCK_SIZE;

        //for each pixel in this block
        for (int x = 0; x < BLOCK_SIZE; x++){
            for (int y = 0; y < BLOCK_SIZE; y++) {

                //0xFF to remove the sign
                sum += tileData[(yOffset+y)*TILE_SIZE + (xOffset+x)] & 0xFF;
            }
        }
        //normalising to [0..1] value
        return sum /(count*MAX_PIXEL_VALUE);
    }

    /**
     * Decompress a png file for tile into a TILE_SIZE x TILE_SIZE byte array (if used on tile)
     *
     * @param tileData : byte array with tile data
     * @return byte array of size TILE_SIZE x TILE_SIZE containing each pixel of the tile
     * @throws RuntimeException
     * */
    public byte[] decompressPNGFile(@NonNull byte[] tileData)throws RuntimeException{


        try {
            BufferedImage img = ImageIO.read(new ByteArrayInputStream(tileData));
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
}
