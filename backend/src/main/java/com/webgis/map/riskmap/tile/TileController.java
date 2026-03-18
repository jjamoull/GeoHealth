package com.webgis.map.riskmap.tile;

import com.webgis.MessageDto;
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

    public TileController(TileRepository tileRepository, TileService tileService){
        this.tileRepository = tileRepository;
        this.tileService = tileService;
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



    //to delete later, testing route
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
}
