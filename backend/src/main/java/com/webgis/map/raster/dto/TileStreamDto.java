package com.webgis.map.raster.dto;

import java.util.Arrays;

public record TileStreamDto(byte[] data) {
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof TileStreamDto other)) return false;
        return Arrays.equals(data, other.data);
    }

    @Override
    public int hashCode() {
        return Arrays.hashCode(data);
    }

    @Override
    public String toString() {
        return "TileStreamDto{data :{ \n " + Arrays.toString(data) + " }\n}";
    }
}
