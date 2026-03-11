package com.webgis.map.riskmap.tile;

import org.springframework.stereotype.Service;

@Service
public class TileService {
    private final TileRepository tileRepository;

    public TileService (TileRepository tileRepository){
        this.tileRepository = tileRepository;
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
        final TileId tileId = new TileId(mapId, zoom, x, y);
        final Tile tileToStore = new Tile(tileId, data);

        return tileRepository.save(tileToStore);
    }
}
