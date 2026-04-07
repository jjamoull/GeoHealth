package com.webgis.map.tile;

import com.webgis.MessageDto;
import com.webgis.map.raster.dto.TileMeanAndXYdto;
import com.webgis.map.raster.RasterMapService;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@RestController
@RequestMapping("/tile")
public class TileController {

    private final TileRepository tileRepository;
    private final TileService tileService;
    private final RasterMapService riskFactorMapService;

    public TileController(TileRepository tileRepository, TileService tileService, RasterMapService riskFactorMapService){
        this.tileRepository = tileRepository;
        this.tileService = tileService;
        this.riskFactorMapService = riskFactorMapService;
    }

    @GetMapping("/file/{mapId}/{z}/{x}/{y}.png")
    public ResponseEntity<Object> getTileByXYZ(@PathVariable Long mapId,
                                          @PathVariable int z,
                                          @PathVariable int x,
                                          @PathVariable int y) {
        final Optional<Tile> tile = tileService.findById(new TileId(mapId, z, x, y));
        if (tile.isPresent()){
            return ResponseEntity.status(200).contentType(MediaType.IMAGE_PNG).body(tile.get().getTileData());
        }else {
            return ResponseEntity.status(500).body((("Tile for map :" + mapId + " doesn't exist")));
        }
    }

    @GetMapping("/file/mean/{mapId}/{z}/{lat}/{lng}")
    public ResponseEntity<Object> getTileMeanBlock(@PathVariable Long mapId,
                                                        @PathVariable int z,
                                                        @PathVariable double lat,
                                                        @PathVariable double lng){

        final int[] tileCoordinates = tileService.getTileCoordinates(z,lat,lng);

        final Optional<Tile> tile = tileService.findById(new TileId(mapId, z, tileCoordinates[0], tileCoordinates[1]));

        final int[] blockCoordinates = tileService.getBlockCoordinates(z, lat, lng);

        if (tile.isPresent()){
            //extracting pixels from stored file
            final byte[] tilePixels = tileService.decompressPNGFile(tile.get().getTileData());

            final float mean = tileService.getTileMeanBlock(tilePixels, blockCoordinates);


            final TileMeanAndXYdto dto = new TileMeanAndXYdto(mean,
                    tileCoordinates[0],
                    tileCoordinates[1],
                    blockCoordinates[0],
                    blockCoordinates[1]);
            return ResponseEntity.status(200).body(dto);
        }else{
            return ResponseEntity.status(404).body(new MessageDto("Not able to find mean for tile " + mapId));
        }
    }


}
