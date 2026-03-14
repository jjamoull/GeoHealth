package com.webgis.map.riskmap.tile;

import org.springframework.data.jpa.repository.JpaRepository;

public interface TileRepository extends JpaRepository<Tile, TileId> {
}
