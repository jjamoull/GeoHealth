package com.webgis.map.riskmap.tile;

import com.webgis.MessageDto;
import com.webgis.map.riskmap.dto.TileMeanAndXYdto;
import com.webgis.map.riskmap.riskfactormap.RiskFactorMapService;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;
import java.util.Optional;

@RestController
@RequestMapping("/tile")
public class TileController {

    private final TileRepository tileRepository;
    private final TileService tileService;
    final int BLOCK_SIZE = TileConstants.BLOCK_SIZE;
    private final RiskFactorMapService riskFactorMapService;

    public TileController(TileRepository tileRepository, TileService tileService, RiskFactorMapService riskFactorMapService){
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


    @GetMapping("/file/{mapId}/{z}/{x}/{y}/{meanIndex}")
    public ResponseEntity<Object> getTileMeanBlockIndex(@PathVariable Long mapId,
                                                               @PathVariable int z,
                                                               @PathVariable int x,
                                                               @PathVariable int y,
                                                               @PathVariable int meanIndex){
        final Optional<Tile> tile = tileService.findById(new TileId(mapId, z, x, y));

        if (tile.isPresent()){
            return ResponseEntity.status(200).body((float) (tile.get().getTileMeans()[meanIndex] & 0xFF));
        }else{
            return ResponseEntity.status(404).body(new MessageDto("Not able to find mean for tile " + mapId));
        }
    }



    //ToDo delete later, testing route
    @GetMapping("/file/{mapId}/{z}/{x}/{y}/array")
    public ResponseEntity<Object> getTileMeanBlockIndex(@PathVariable Long mapId,
                                                        @PathVariable int z,
                                                        @PathVariable int x,
                                                        @PathVariable int y){
        final Optional<Tile> tile = tileService.findById(new TileId(mapId, z, x, y));

        byte[] byteArray = tile.get().getTileMeans();
        float[] floatArray = new float[byteArray.length];

        for (int i = 0; i < byteArray.length; i++) {
            floatArray[i] = (byteArray[i] & 0xFF) / 255.0f;
        }


        if (tile.isPresent()){
            return ResponseEntity.status(200).body(Arrays.toString(floatArray));
        }else{
            return ResponseEntity.status(404).body(new MessageDto("Not able to find mean for tile " + mapId));
        }

    }


    @GetMapping("/file/mean/{mapId}/{z}/{lat}/{lng}")
    public ResponseEntity<Object> getTileMeanBlock(@PathVariable Long mapId,
                                                        @PathVariable int z,
                                                        @PathVariable double lat,
                                                        @PathVariable double lng){

        final int[] tileCoordinates = tileService.getTileCoordinates(z,lat,lng);

        final Optional<Tile> tile = tileService.findById(new TileId(mapId, z, tileCoordinates[0], tileCoordinates[1]));

        int[] blockCoordinates = tileService.getBlockCoordinates(z, lat, lng);

        if (tile.isPresent()){
            float mean = (tile.get().getTileMeans()[
                    tileService.getMeanIndex(blockCoordinates[0],
                    blockCoordinates[1])
                    ] & 0xFF) / 255.0f;

            TileMeanAndXYdto dto = new TileMeanAndXYdto(mean, tileCoordinates[0], tileCoordinates[1]);
            return ResponseEntity.status(200).body(dto);
        }else{
            return ResponseEntity.status(404).body(new MessageDto("Not able to find mean for tile " + mapId));
        }
    }


}
