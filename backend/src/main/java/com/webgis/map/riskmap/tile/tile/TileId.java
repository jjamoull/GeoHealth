package com.webgis.map.riskmap.tile.tile;

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

    public int getZoom() {
        return zoom;
    }

    public int getY() {
        return y;
    }


    public int getX() {
        return x;
    }

    public void setMapId(Long mapId) {
        this.mapId = mapId;
    }

    public void setZoom(int zoom) {
        this.zoom = zoom;
    }

    public void setY(int y) {
        this.y = y;
    }

    public void setX(int x) {
        this.x = x;
    }






}