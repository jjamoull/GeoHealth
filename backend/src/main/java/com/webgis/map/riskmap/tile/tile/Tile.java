package com.webgis.map.riskmap.tile.tile;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Lob;
import jakarta.persistence.Column;

@Entity
@Table(name = "tile")
public class Tile {

    @EmbeddedId
    private TileId tileId;

    @Lob
    @Column(name = "tile_data", nullable = false)
    private byte[] tileData;


    public Tile(){}

    public Tile(TileId tileId,
                         byte[] tileData){
        this.tileId = tileId;
        this.tileData =tileData;
    }

    /* ***************************************
     * **************** GETTER ***************
     * ****************************************/

    public TileId getTileId() {
        return tileId;
    }

    public byte[] getTileData() {
        return tileData;
    }

    /** ***************************************
     * *************** SETTER *****************
     * **************************************** */

    public void setTileData(byte[] tileData) {
        this.tileData = tileData;
    }

    public void setTileId(TileId tileId){
        this.tileId = tileId;
    }

}



