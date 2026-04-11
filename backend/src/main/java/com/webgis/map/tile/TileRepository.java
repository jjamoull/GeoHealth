package com.webgis.map.tile;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface TileRepository extends JpaRepository<Tile, TileId> {
    Optional<Tile> findByTileId(TileId tileId);

}
