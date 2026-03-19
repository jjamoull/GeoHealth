package com.webgis.map.riskmap.tile;

import com.webgis.map.riskmap.riskfactormap.RiskFactorMap;
import com.webgis.map.riskmap.riskfactormap.RiskFactorMapRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class TileService {
    private final TileRepository tileRepository;
    private final RiskFactorMapRepository riskFactorMapRepository;

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
    public Tile save(long mapId, int zoom, int x, int y, byte[] data, byte[] means){
        final RiskFactorMap riskFactorMap = riskFactorMapRepository.findById(mapId)
                .orElseThrow(() -> new RuntimeException("Map not found: " + mapId));

        final TileId tileId = new TileId(mapId, zoom, x, y);
        final Tile tileToStore = new Tile(tileId, data, means, riskFactorMap);

        return tileRepository.save(tileToStore);
    }

    public Optional<Tile> findById(TileId tileId) {
        return tileRepository.findById(tileId);
    }
}
