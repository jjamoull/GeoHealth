package com.webgis.map.tile;

import com.webgis.map.raster.RasterMap;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.MapsId;
import jakarta.persistence.Table;
import jakarta.persistence.Lob;
import jakarta.persistence.Column;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.JoinColumn;

@Entity
@Table(name = "tile")
public class Tile {
    @EmbeddedId
    private TileId tileId;
    @Lob
    @Column(name = "tile_data", nullable = false)
    private byte[] tileData;


    @ManyToOne
    @MapsId("mapId")
    @JoinColumn(name = "map_id")
    private RasterMap rasterMap;

    public Tile(){}

    public Tile(TileId tileId,
                byte[] tileData,
                RasterMap rasterMap){
        this.tileId = tileId;
        this.tileData = tileData;
        this.rasterMap = rasterMap;
    }


    public TileId getTileId() {
        return tileId;
    }

    public byte[] getTileData() {
        return tileData;
    }

    public RasterMap getRasterMap() { return rasterMap; }

    public void setTileData(byte[] tileData) {
        this.tileData = tileData;
    }

    public void setTileId(TileId tileId){
        this.tileId = tileId;
    }

    public void setRiskFactorMap(RasterMap rasterMap) { this.rasterMap = rasterMap; }
}



