package com.webgis.map.riskmap.tile;

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

    public TileController(TileRepository tileRepository){
        this.tileRepository = tileRepository;
    }

    @GetMapping("/file/{mapId}/{z}/{x}/{y}.png")
    public ResponseEntity<byte[]> getTileByXYZ(@PathVariable Long mapId,
                                          @PathVariable int z,
                                          @PathVariable int x,
                                          @PathVariable int y) {
        final Optional<Tile> tile = tileRepository.findByTileId(new TileId(mapId, z, x, y));
        if (tile.isPresent()){
            return ResponseEntity.status(200).contentType(MediaType.IMAGE_PNG).body(tile.get().getTileData());
        }else {
            return ResponseEntity.status(404).body((("Tile for map :" + mapId + " doesn't exist").getBytes()));
        }
    }
}
