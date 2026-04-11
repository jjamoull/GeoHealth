package com.webgis.map.tile;

import jakarta.persistence.Embeddable;

import java.io.Serializable;
import java.util.Objects;

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


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof TileId)) return false;

        final TileId tileId = (TileId) o;

        if (zoom != tileId.zoom){
            return false;
        }
        if (x != tileId.x) {
            return false;
        }
        if (y != tileId.y) {
            return false;
        }
        return Objects.equals(mapId, tileId.mapId);
    }

    @Override
    public int hashCode() {
        int result;
        if (mapId != null) {
            result = mapId.hashCode();
        } else {
            result = 0;
        }

        result = 31 * result + zoom;
        result = 31 * result + x;
        result = 31 * result + y;

        return result;
    }



}