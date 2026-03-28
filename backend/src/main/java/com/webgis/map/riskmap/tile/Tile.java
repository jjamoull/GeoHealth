package com.webgis.map.riskmap.tile;

import com.webgis.map.riskmap.riskfactormap.RiskFactorMap;
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
    private RiskFactorMap riskFactorMap;

    public Tile(){}

    public Tile(TileId tileId,
                byte[] tileData,
                RiskFactorMap riskFactorMap){
        this.tileId = tileId;
        this.tileData = tileData;
        this.riskFactorMap = riskFactorMap;
    }


    public TileId getTileId() {
        return tileId;
    }

    public byte[] getTileData() {
        return tileData;
    }

    public RiskFactorMap getRiskFactorMap() { return riskFactorMap; }

    public void setTileData(byte[] tileData) {
        this.tileData = tileData;
    }

    public void setTileId(TileId tileId){
        this.tileId = tileId;
    }

    public void setRiskFactorMap(RiskFactorMap riskFactorMap) { this.riskFactorMap = riskFactorMap; }
}



