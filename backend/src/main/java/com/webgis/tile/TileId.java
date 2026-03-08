package com.webgis.tile;

import jakarta.persistence.Embeddable;

import java.io.Serializable;

@Embeddable
public class TileId implements Serializable {
    private Long mapId;
    private int zoom;
    private int x;
    private int y;

    public TileId(Long mapId, int zoom, int x, int y) {
        this.mapId = mapId;
        this.zoom = zoom;
        this.x = x;
        this.y = y;
    }

    public TileId() {}

    public Long getMapId(){
        return this.mapId;
    }

    //ToDo

}