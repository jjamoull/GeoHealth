package com.webgis.map.tile;

import com.webgis.map.raster.RasterMap;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface TileRepository extends JpaRepository<Tile, TileId> {
    Optional<Tile> findByTileId(TileId tileId);
    List<Tile> findTileByRasterMap(RasterMap rasterMap);
}
